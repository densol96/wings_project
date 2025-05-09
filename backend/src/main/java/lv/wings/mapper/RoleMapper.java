package lv.wings.mapper;


import org.mapstruct.Mapper;
import lv.wings.dto.response.admin.roles.DetailedRoleDto;
import lv.wings.dto.response.admin.roles.RoleDto;
import lv.wings.model.security.Role;

@Mapper(componentModel = "spring", uses = PermissionMapper.class)
public interface RoleMapper {
    RoleDto toDto(Role role);

    DetailedRoleDto toDetailedDto(Role role);
}
