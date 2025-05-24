package lv.wings.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import lv.wings.dto.response.admin.roles.PermissionDto;
import lv.wings.exception.validation.InvalidFieldsException;
import lv.wings.mapper.PermissionMapper;
import lv.wings.model.security.Permission;
import lv.wings.repo.PermissionRepository;
import lv.wings.service.AbstractCRUDService;
import lv.wings.service.PermissionService;

@Service
public class PermissionServiceImpl extends AbstractCRUDService<Permission, Integer> implements PermissionService {

    private final PermissionRepository permissionRepo;
    private final PermissionMapper permissionMapper;

    public PermissionServiceImpl(PermissionRepository repository, PermissionMapper permissionMapper) {
        super(repository, "Permission", "entity.permission");
        this.permissionRepo = repository;
        this.permissionMapper = permissionMapper;
    }

    @Override
    public List<PermissionDto> getAll() {
        return permissionRepo.findAll().stream().map(permissionMapper::toDto).toList();
    }

    @Override
    public List<Permission> validatePermissionInputAndReturnEntities(List<Integer> permissionIds) {
        if (permissionIds == null || permissionIds.isEmpty())
            return List.of();
        List<Permission> permissions = repository.findAllById(permissionIds);
        if (permissions.size() != permissionIds.size()) {
            Map<String, String> invalidFields = new HashMap<>();
            invalidFields.put("permissionCodes", "permissions.invalid");
            throw new InvalidFieldsException(invalidFields);
        }
        return permissions;
    }
}
