package lv.wings.service.impl;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import io.micrometer.common.lang.Nullable;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import lv.wings.config.security.MyUserDetails;
import lv.wings.config.security.UserSecurityService;
import lv.wings.dto.request.users.EmailDto;
import lv.wings.dto.request.users.LoginDto;
import lv.wings.dto.request.users.NewUserDto;
import lv.wings.dto.request.users.PasswordDto;
import lv.wings.dto.request.users.ResetPasswordDto;
import lv.wings.dto.request.users.UsernameDto;
import lv.wings.dto.response.BasicMessageDto;
import lv.wings.dto.response.users.AuthResponseDto;
import lv.wings.dto.response.users.UserSessionInfoDto;
import lv.wings.enums.RedisKeyType;
import lv.wings.enums.SecurityEventType;
import lv.wings.exception.other.TokenNotFoundException;
import lv.wings.exception.validation.InvalidFieldsException;
import lv.wings.exception.validation.PasswordsMismatchException;
import lv.wings.mapper.UserMapper;
import lv.wings.model.security.User;
import lv.wings.repo.UserRepository;
import lv.wings.service.AuthService;
import lv.wings.service.EmailSenderService;
import lv.wings.service.JwtService;
import lv.wings.service.LocaleService;
import lv.wings.service.SecurityEventService;
import lv.wings.service.TokenStoreService;
import lv.wings.util.HashUtils;
import lv.wings.util.UrlAssembler;

@Service
public class AuthServiceImpl implements AuthService {

   private final JwtService jwtService;
   private final PasswordEncoder passwordEncoder;
   private final AuthenticationManager authManager;
   private final SecurityEventService securirtyEventService;
   private final UserSecurityService userSecurityService;
   private final UserRepository userRepo;
   private final UserMapper userMapper;
   private final EmailSenderService emailSenderService;
   private final TokenStoreService tokenStoreService;
   private final LocaleService localeService;

   public AuthServiceImpl(
         UserRepository userRepo,
         JwtService jwtService,
         PasswordEncoder passwordEncoder,
         AuthenticationManager authManager,
         SecurityEventService securirtyEventService,
         UserMapper userMapper,
         UserSecurityService userSecurityService,
         EmailSenderService emailSenderService,
         TokenStoreService tokenStoreService,
         LocaleService localeService) {
      this.userRepo = userRepo;
      this.jwtService = jwtService;
      this.passwordEncoder = passwordEncoder;
      this.authManager = authManager;
      this.securirtyEventService = securirtyEventService;
      this.userMapper = userMapper;
      this.userSecurityService = userSecurityService;
      this.emailSenderService = emailSenderService;
      this.tokenStoreService = tokenStoreService;
      this.localeService = localeService;
   }

   @Override
   public AuthResponseDto authenticate(LoginDto request) {
      Optional<User> user = userRepo.findByUsername(request.getUsername());
      try {
         Authentication authentication = authManager.authenticate(
               new UsernamePasswordAuthenticationToken(
                     request.getUsername(),
                     request.getPassword()));
         MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
         User currentUser = userDetails.getUser();
         securirtyEventService.handleSecurityEvent(currentUser, SecurityEventType.LOGIN_SUCCESS, null);
         return new AuthResponseDto(jwtService.generateToken(currentUser));
      } catch (AuthenticationException e) {
         user.ifPresent(u -> {
            securirtyEventService.handleSecurityEvent(u, SecurityEventType.LOGIN_FAILED, null);
         });
         throw e;
      }
   }

   @Override
   public UserSessionInfoDto getLoggedInUserInfo() {
      User user = userSecurityService.getCurrentUserDetails().getUser();
      return userMapper.userToSessionInfo(user, userSecurityService.getUserAuthorities(user));
   }

   @Override
   public BasicMessageDto requestToUnlockAccount(String token) {
      User user = parseTokenAndExtractUser(token, RedisKeyType.REQUEST_UNLOCK);
      String randomToken = HashUtils.createRandomToken();
      String hashedRandomToken = HashUtils.createTokenHash(randomToken);
      tokenStoreService.storeToken(RedisKeyType.ACCOUNT_UNLOCK, hashedRandomToken, user.getId(), Duration.ofMinutes(5));
      emailSenderService.sendEmailToUnlockAccount(user, UrlAssembler.getFullFrontendPath("/admin/unlock-account/" + randomToken));
      return new BasicMessageDto(localeService.getMessage("unlock-email.sent"));
   }

   @Override
   public BasicMessageDto unlockAccount(String token) {
      User user = parseTokenAndExtractUser(token, RedisKeyType.ACCOUNT_UNLOCK);
      user.setAccountLocked(false);
      user.setLoginAttempts(0);
      userRepo.save(user);
      securirtyEventService.handleSecurityEvent(user, SecurityEventType.ACCOUNT_UNLOCKED, null);
      return new BasicMessageDto(localeService.getMessage("user.unlocked"));
   }

   @Override
   public BasicMessageDto requestToResetPassword(UsernameDto usernameDto) {
      userRepo.findByUsername(usernameDto.getUsername()).ifPresent(user -> {
         // to prevent spamming
         if (!tokenStoreService.hasActiveTokenOfThisType(RedisKeyType.PASSWORD_RESET, user.getId())) {
            String randomToken = HashUtils.createRandomToken();
            String hashedRandomToken = HashUtils.createTokenHash(randomToken);
            tokenStoreService.storeToken(RedisKeyType.PASSWORD_RESET, hashedRandomToken, user.getId(), Duration.ofMinutes(5));
            emailSenderService.sendPasswordResetToken(user, UrlAssembler.getFullFrontendPath("/admin/reset-password/" + randomToken));
         }
      });
      return new BasicMessageDto(localeService.getMessage("password-reset.instruction-sent"));
   }

   @Override
   public BasicMessageDto resetPassword(String resetPasswordToken, ResetPasswordDto dto) {
      if (!dto.getPassword().equals(dto.getConfirmPassword())) {
         throw new PasswordsMismatchException("Password and passwordConfirm do not match in AuthService.resetPassword");
      }
      User user = parseTokenAndExtractUser(resetPasswordToken, RedisKeyType.PASSWORD_RESET);
      user.setPassword(passwordEncoder.encode(dto.getPassword()));
      userRepo.save(user);
      securirtyEventService
            .handleSecurityEvent(user, SecurityEventType.PASSWORD_CHANGED, "Parole tika atiestatīta, izmantojot paroles atjaunošanas saiti");
      return new BasicMessageDto(localeService.getMessage("password-reset.successfull"));
   }

   @Override
   public BasicMessageDto changeEmail(EmailDto emailDto) {
      User user = userSecurityService.getCurrentUserDetails().getUser();
      String newEmail = emailDto.getEmail();
      validateEmailUnique(newEmail, user);
      String oldEmail = user.getEmail();
      user.setEmail(emailDto.getEmail());
      userRepo.save(user);
      emailSenderService.sendEmailChangeNotification(user, oldEmail, user.getEmail());
      securirtyEventService.handleSecurityEvent(user, SecurityEventType.EMAIL_CHANGED, "E-pasti nomainīja pats lietotājs");
      return new BasicMessageDto(localeService.getMessage("email-change.successfull"));
   }

   @Override
   public BasicMessageDto changePassword(PasswordDto dto) {
      User user = userSecurityService.getCurrentUserDetails().getUser();
      validateChangePasswordInput(dto, user);
      user.setPassword(passwordEncoder.encode(dto.getPassword()));
      userRepo.save(user);
      emailSenderService.sendPasswordChangeNotification(user);
      securirtyEventService.handleSecurityEvent(user, SecurityEventType.PASSWORD_CHANGED, "Paroli nomainīja pats lietotājs");
      return new BasicMessageDto(localeService.getMessage("password-change.successfull"));
   }

   private User parseTokenAndExtractUser(String token, RedisKeyType redisKeyType) {
      String hashedToken = HashUtils.createTokenHash(token);
      Integer userId = tokenStoreService.getUserIdByToken(redisKeyType, hashedToken);
      if (userId == null)
         throw new TokenNotFoundException("Unable to find the token - " + redisKeyType.buildKey(hashedToken));
      tokenStoreService.deleteToken(redisKeyType, hashedToken);
      User user = userRepo.findById(userId)
            .orElseThrow(
                  () -> new TokenNotFoundException("Unable to find the requested user with the id of {} from token - " + redisKeyType.buildKey(hashedToken)));
      return user;
   }

   private void validateChangePasswordInput(PasswordDto dto, User user) {
      Map<String, String> takenFields = new HashMap<>();
      if (!passwordEncoder.matches(dto.getOldPassword(), user.getPassword())) {
         takenFields.put("oldPassword", "error.password");
      }
      if (!dto.getPassword().equals(dto.getConfirmPassword())) {
         takenFields.put("confirmPassword", "passwords.mismatch");
      }
      if (!takenFields.isEmpty())
         throw new InvalidFieldsException(takenFields);
   }

   private void validateEmailUnique(String email, @Nullable User user) {
      Map<String, String> takenFields = new HashMap<>();

      boolean isCreating = user == null;

      if ((isCreating || !email.equals(user.getEmail())) && userRepo.existsByEmail(email)) {
         takenFields.put("email", "email.taken");
      }

      if (!takenFields.isEmpty()) {
         throw new InvalidFieldsException(takenFields);
      }
   }
}
