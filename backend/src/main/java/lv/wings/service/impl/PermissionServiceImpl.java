package lv.wings.service.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.NonNull;
import lv.wings.enums.PermissionName;
import lv.wings.exception.validation.InvalidFieldsException;
import lv.wings.model.security.Permission;
import lv.wings.repo.PermissionRepository;
import lv.wings.service.AbstractCRUDService;
import lv.wings.service.PermissionService;

public class PermissionServiceImpl extends AbstractCRUDService<Permission, Integer> implements PermissionService {

    private final PermissionRepository permissionRepo;

    public PermissionServiceImpl(PermissionRepository repository) {
        super(repository, "Permission", "entity.permission");
        this.permissionRepo = repository;
    }

    public Set<Permission> validatePermissionInputAndReturnEntities(@NonNull List<String> permissionCodes) {
        Map<String, String> invalidFields = new HashMap<>();
        invalidFields.put("permissionCodes", "permissions.invalid");

        Set<Permission> permissions = new HashSet<>();

        for (String code : permissionCodes) {
            try {
                PermissionName permissionEnum = PermissionName.valueOf(code.toUpperCase());
                Permission permission = permissionRepo.findByName(permissionEnum).orElseThrow(() -> new InvalidFieldsException(invalidFields));
                permissions.add(permission);
            } catch (IllegalArgumentException e) {
                throw new InvalidFieldsException(invalidFields);
            }
        }
        return permissions;
    }


}
