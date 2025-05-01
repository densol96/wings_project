package lv.wings.service.impl;


import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import lv.wings.config.security.MyUserDetails;
import lv.wings.dto.request.users.LoginDto;
import lv.wings.dto.request.users.NewUserDto;
import lv.wings.dto.response.users.AuthResponseDto;
import lv.wings.enums.SecurityEventType;
import lv.wings.model.security.User;
import lv.wings.repo.UserRepository;
import lv.wings.service.AuthService;
import lv.wings.service.JwtService;
import lv.wings.service.SecurityEventService;

@Service
public class AuthServiceImpl implements AuthService {

   private final UserRepository userRepo;
   private final JwtService jwtService;
   private final PasswordEncoder passwordEncoder;
   private final AuthenticationManager authManager;
   private final SecurityEventService securirtyEventService;

   public AuthServiceImpl(UserRepository userRepo, JwtService jwtService, PasswordEncoder passwordEncoder, AuthenticationManager authManager,
         SecurityEventService securirtyEventService) {
      this.userRepo = userRepo;
      this.jwtService = jwtService;
      this.passwordEncoder = passwordEncoder;
      this.authManager = authManager;
      this.securirtyEventService = securirtyEventService;
   }

   public AuthResponseDto register(NewUserDto dto) {
      User user = User.builder()
            .email(dto.getEmail())
            .username(dto.getUsername())
            .password(passwordEncoder.encode(dto.getPassword())).build();
      userRepo.save(user);
      return new AuthResponseDto(jwtService.generateToken(user));
   }

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

}
