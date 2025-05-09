package lv.wings.service;

import java.util.List;
import lv.wings.dto.response.admin.roles.PermissionDto;
import lv.wings.model.security.Permission;

public interface PermissionService extends CRUDService<Permission, Integer> {
    List<Permission> validatePermissionInputAndReturnEntities(List<Integer> permissionCodes);

    List<PermissionDto> getAll();
}
