package lv.wings.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import lombok.NonNull;

import lv.wings.dto.response.admin.users.UserAdminDto;
import lv.wings.exception.validation.InvalidParameterException;
import lv.wings.mapper.UserMapper;
import lv.wings.model.security.User;
import lv.wings.repo.UserRepository;
import lv.wings.service.AbstractCRUDService;
import lv.wings.service.UserService;
import lv.wings.util.CustomValidator;

@Service
public class UserServiceImpl extends AbstractCRUDService<User, Integer> implements UserService {

    private final UserRepository userRepo;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository repository, UserMapper userMapper) {
        super(repository, "User", "entity.user");
        this.userRepo = repository;
        this.userMapper = userMapper;
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
                .map(userMapper::userToAdminDto)
                .toList();
    }
}
