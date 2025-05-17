package lv.wings.service.impl;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.micrometer.common.lang.Nullable;

import jakarta.transaction.Transactional;

import lv.wings.config.security.UserSecurityService;
import lv.wings.dto.interfaces.HasEmailAndUsername;
import lv.wings.dto.request.admin.AdminPasswordDto;
import lv.wings.dto.request.admin.NewUserDetailsDto;
import lv.wings.dto.response.BasicMessageDto;
import lv.wings.dto.response.admin.users.UserAdminDto;
import lv.wings.dto.response.admin.users.UserDetailsDto;
import lv.wings.enums.AccountStatus;
import lv.wings.enums.SecurityEventType;
import lv.wings.exception.validation.InvalidFieldsException;
import lv.wings.mapper.UserMapper;
import lv.wings.model.security.Role;
import lv.wings.model.security.User;
import lv.wings.repo.UserRepository;
import lv.wings.service.AbstractCRUDService;
import lv.wings.service.EmailSenderService;
import lv.wings.service.RoleService;
import lv.wings.service.SecurityEventService;
import lv.wings.service.UserService;
import lv.wings.util.CustomValidator;
import lv.wings.util.HashUtils;

@Service
public class UserServiceImpl extends AbstractCRUDService<User, Integer> implements UserService {

    private final UserRepository userRepo;
    private final UserMapper userMapper;
    private final RoleService roleService;
    private final EmailSenderService emailSenderService;
    private final PasswordEncoder passwordEncoder;
    private final SecurityEventService securityEventService;
    private final UserSecurityService userSecurityService;

    public UserServiceImpl(
            UserRepository repository,
            UserMapper userMapper,
            RoleService roleService,
            EmailSenderService emailSenderService,
            PasswordEncoder passwordEncoder,
            SecurityEventService securityEventService,
            UserSecurityService userSecurityService) {
        super(repository, "User", "entity.user");
        this.userRepo = repository;
        this.userMapper = userMapper;
        this.roleService = roleService;
        this.emailSenderService = emailSenderService;
        this.passwordEncoder = passwordEncoder;
        this.securityEventService = securityEventService;
        this.userSecurityService = userSecurityService;
    }

    @Override
    public void updateLastActivity(Integer id) {
        CustomValidator.isValidId(id);
        userRepo.updateLastActivity(id, LocalDateTime.now());
    }

    @Override
    public List<UserAdminDto> getAllEmployees(String status, String sortBy, String direction) {
        Sort.Direction sortDirection = direction.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(sortDirection, sortBy);
        List<User> users;

        switch (status.toLowerCase()) {
            case "active" -> users = userRepo.findByAccountLockedFalseAndAccountBannedFalse(sort);
            case "inactive" -> users = userRepo.findByAccountLockedTrueOrAccountBannedTrue(sort);
            default -> users = userRepo.findAll(sort);
        }

        return users.stream()
                .filter(user -> !user.isSystemUser())
                .map(user -> userMapper.userToAdminDto(user, mapToAccountStatus(user)))
                .toList();
    }

    private String mapToAccountStatus(User user) {
        AccountStatus status;

        if (user.isAccountBanned()) {
            status = AccountStatus.BANNED;
        } else if (user.isAccountLocked()) {
            status = AccountStatus.LOCKED;
        } else {
            status = AccountStatus.ACTIVE;
        }

        return status.getLabel("lv");
    }

    @Override
    public UserDetailsDto getEmployeeData(Integer id) {
        return userMapper.usetToDetails(findById(id));
    }

    @Override
    @Transactional
    public BasicMessageDto createNewEmployee(NewUserDetailsDto dto) {
        User user = new User();

        validateEmailAndUsernameUniqueness(dto, null);

        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());

        List<Role> roles = roleService.findByIds(dto.getRoles());
        user.setRoles(roles);

        // Will create a random password that will be sent to the user email. Then, user can change the password if required.
        String password = HashUtils.createRandomToken().substring(0, 10);

        user.setPassword(passwordEncoder.encode(password));
        userRepo.save(user);

        securityEventService.handleSecurityEvent(user, SecurityEventType.NEW_USER_REGISTERED, null);

        emailSenderService.sendNewEmployeeNotification(user, password);

        return new BasicMessageDto("Jauns lietotājs ir pievienots, un parole piekļuvei tika nosūtīta uz norādīto e-pastu: " + user.getEmail());
    }

    @Override
    public BasicMessageDto updateUser(Integer id, UserDetailsDto dto) {
        User user = findById(id);

        validateEmailAndUsernameUniqueness(dto, user);

        User admin = userSecurityService.getCurrentUserDetails().getUser();

        String oldEmail = user.getEmail();
        String newEmail = dto.getEmail();

        String oldUsername = user.getUsername();
        String newUsername = dto.getUsername();

        boolean oldAccountLockedStatus = user.isAccountLocked();
        boolean newAccountLockedStatus = dto.getAccountLocked();

        boolean oldAccountBannedStatus = user.isAccountBanned();
        boolean newAccountBannedStatus = dto.getAccountBanned();


        user.setUsername(newUsername);
        user.setEmail(newEmail);
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setAccountLocked(newAccountLockedStatus);
        user.setAccountBanned(newAccountBannedStatus);

        List<Role> roles = roleService.findByIds(dto.getRoles());
        user.setRoles(roles);

        repository.save(user);

        if (oldAccountLockedStatus != newAccountLockedStatus) {
            if (newAccountLockedStatus) {
                securityEventService.handleSecurityEvent(user, SecurityEventType.ACCOUNT_LOCKED,
                        "Kontu bloķēja (locked) administrators " + admin.getUsername());
            } else {
                securityEventService.handleSecurityEvent(user, SecurityEventType.ACCOUNT_UNLOCKED,
                        "Konta bloķēšana (locked) noņema administrators " + admin.getUsername());
            }
        }

        if (oldAccountBannedStatus != newAccountBannedStatus) {
            if (newAccountBannedStatus) {
                securityEventService.handleSecurityEvent(user, SecurityEventType.ACCOUNT_BANNED,
                        "Kontu aizliedza (banned) administrators " + admin.getUsername());
            } else {
                securityEventService.handleSecurityEvent(user, SecurityEventType.ACCOUNT_UNBANNED,
                        "Kontu aizliegumu (banned) noņema administrators " + admin.getUsername());
            }
        }

        if (!oldEmail.equals(newEmail)) {
            securityEventService.handleSecurityEvent(user, SecurityEventType.EMAIL_CHANGED, "E-pastu nomainīja administrators " + admin.getUsername());
            emailSenderService.sendEmailChangeNotification(user, oldEmail, newEmail);
        }

        if (!oldUsername.equals(newUsername)) {
            securityEventService.handleSecurityEvent(user, SecurityEventType.USERNAME_CHANGED, "Lietotājvārdu nomainīja administrators " + admin.getUsername());
            emailSenderService.sendNewUsernameNotification(user);
        }

        return new BasicMessageDto("Lietotāja dati veiksmīgi atjaunināti.");
    }


    @Override
    public BasicMessageDto updatePassword(Integer id, AdminPasswordDto dto) {
        User user = findById(id);

        validateChangePasswordInput(dto);

        String password = dto.getPassword();

        user.setPassword(passwordEncoder.encode(password));
        userRepo.save(user);

        emailSenderService.sendAdminChangedPasswordNotification(user, password);

        User admin = userSecurityService.getCurrentUserDetails().getUser();
        securityEventService.handleSecurityEvent(user, SecurityEventType.PASSWORD_CHANGED, "Paroli nomainīja administrators " + admin.getUsername());

        return new BasicMessageDto("Jauns lietotājs ir pievienots, un parole piekļuvei tika nosūtīta uz norādīto e-pastu: " + user.getEmail());
    }

    private void validateChangePasswordInput(AdminPasswordDto dto) {
        Map<String, String> takenFields = new HashMap<>();
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            takenFields.put("confirmPassword", "passwords.mismatch");
        }
        if (!takenFields.isEmpty())
            throw new InvalidFieldsException(takenFields);
    }

    private void validateEmailAndUsernameUniqueness(HasEmailAndUsername dto, @Nullable User currentUser) {
        Map<String, String> takenFields = new HashMap<>();

        boolean isCreating = currentUser == null;

        if ((isCreating || !dto.getEmail().equals(currentUser.getEmail()))
                && userRepo.existsByEmail(dto.getEmail())) {
            takenFields.put("email", "email.taken");
        }

        if ((isCreating || !dto.getUsername().equals(currentUser.getUsername()))
                && userRepo.existsByUsername(dto.getUsername())) {
            takenFields.put("username", "username.taken");
        }

        if (!takenFields.isEmpty()) {
            throw new InvalidFieldsException(takenFields);
        }
    }
}
