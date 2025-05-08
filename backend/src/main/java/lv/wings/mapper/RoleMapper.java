package lv.wings.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import lv.wings.dto.response.admin.roles.DetailedRoleDto;
import lv.wings.dto.response.admin.roles.PermissionDto;
import lv.wings.dto.response.admin.roles.RoleDto;
import lv.wings.model.security.Permission;
import lv.wings.model.security.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleDto toDto(Role role);

    DetailedRoleDto toDetailedDto(Role role);

    @Mapping(target = "name", expression = "java(permission.getName().name())")
    @Mapping(target = "label", expression = "java(permission.getName().getLabel(\"lv\"))")
    PermissionDto permissionToDto(Permission permission);
}
