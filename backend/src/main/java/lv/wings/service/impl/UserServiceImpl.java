package lv.wings.service.impl;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import lv.wings.config.security.UserSecurityService;
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
    public BasicMessageDto updateUser(Integer id, UserDetailsDto dto) {
        User user = findById(id);

        String oldEmail = user.getEmail();
        String newEmail = dto.getEmail();

        String oldUsername = user.getUsername();
        String newUsername = dto.getUsername();

        user.setUsername(newUsername);
        user.setEmail(newEmail);
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setAccountLocked(dto.getAccountLocked());
        user.setAccountBanned(dto.getAccountBanned());

        List<Role> roles = roleService.findByIds(dto.getRoles());
        user.setRoles(roles);

        repository.save(user);

        if (!oldEmail.equals(newEmail)) {
            emailSenderService.sendEmailChangeNotification(user, oldEmail, newEmail);
        }

        if (!oldUsername.equals(newUsername)) {
            emailSenderService.sendNewUsernameNotification(user);
        }

        return new BasicMessageDto("Lietotāja dati veiksmīgi atjaunināti.");
    }

    @Override
    @Transactional
    public BasicMessageDto createNewEmployee(NewUserDetailsDto dto) {
        User user = new User();
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

        User currentAdmin = userSecurityService.getCurrentUserDetails().getUser();
        securityEventService.handleSecurityEvent(currentAdmin, SecurityEventType.NEW_USER_REGISTERED,
                "Administrators pievienoja jaunu lietotāju – " + user.getUsername());

        emailSenderService.sendNewEmployeeNotification(user, password);

        return new BasicMessageDto("Jauns lietotājs ir pievienots, un parole piekļuvei tika nosūtīta uz norādīto e-pastu: " + user.getEmail());
    }

    @Override
    public BasicMessageDto updatePassword(Integer id, AdminPasswordDto dto) {
        User user = findById(id);
        validateChangePasswordInput(dto, user);

        String password = dto.getPassword();

        user.setPassword(passwordEncoder.encode(password));
        userRepo.save(user);

        emailSenderService.sendAdminChangedPasswordNotification(user, password);
        User currentAdmin = userSecurityService.getCurrentUserDetails().getUser();

        securityEventService.handleSecurityEvent(currentAdmin, SecurityEventType.PASSWORD_CHANGED,
                "Administrators nomainīja paroli lietotājam – " + user.getUsername());

        return new BasicMessageDto("Jauns lietotājs ir pievienots, un parole piekļuvei tika nosūtīta uz norādīto e-pastu: " + user.getEmail());
    }

    private void validateChangePasswordInput(AdminPasswordDto dto, User user) {
        Map<String, String> takenFields = new HashMap<>();
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            takenFields.put("confirmPassword", "passwords.mismatch");
        }
        if (!takenFields.isEmpty())
            throw new InvalidFieldsException(takenFields);
    }
}
