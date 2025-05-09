package lv.wings.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import lv.wings.dto.response.admin.roles.PermissionDto;
import lv.wings.model.security.Permission;

@Mapper(componentModel = "spring")
public interface PermissionMapper {

    @Mapping(target = "name", expression = "java(permission.getName().name())")
    @Mapping(target = "label", expression = "java(permission.getName().getLabel(\"lv\"))")
    PermissionDto toDto(Permission permission);
}
