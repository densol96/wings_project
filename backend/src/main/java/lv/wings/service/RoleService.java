package lv.wings.service;

import java.util.List;
import java.util.Set;
import lv.wings.dto.response.admin.roles.DetailedRoleDto;
import lv.wings.dto.response.admin.roles.RoleDto;
import lv.wings.model.security.Role;

public interface RoleService extends CRUDService<Role, Integer> {
    Set<Role> findByIds(Set<Integer> ids);

    List<RoleDto> getAllRoles();

    List<DetailedRoleDto> getAllRolesWithDetails();
}
