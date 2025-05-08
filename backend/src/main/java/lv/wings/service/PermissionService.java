package lv.wings.service;

import java.util.List;
import java.util.Set;
import lv.wings.model.security.Permission;

public interface PermissionService extends CRUDService<Permission, Integer> {
    Set<Permission> validatePermissionInputAndReturnEntities(List<String> permissionCodes);
}
