package lv.wings.service;

import java.util.List;
import lv.wings.dto.request.admin.NewRoleDto;
import lv.wings.dto.response.BasicMessageDto;
import lv.wings.dto.response.admin.roles.DetailedRoleDto;
import lv.wings.dto.response.admin.roles.RoleDto;
import lv.wings.model.security.Role;

public interface RoleService extends CRUDService<Role, Integer> {
    List<Role> findByIds(List<Integer> ids);

    List<RoleDto> getAllRoles();

    DetailedRoleDto getRoleData(Integer id);

    List<DetailedRoleDto> getAllRolesWithDetails(List<Integer> permissions);

    BasicMessageDto updateRole(Integer id, NewRoleDto dto);

    BasicMessageDto createRole(NewRoleDto dto);

    BasicMessageDto deleteRole(Integer id);
}
