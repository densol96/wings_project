package lv.wings.dto.response.admin.roles;

import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetailedRoleDto {
    private Integer id;
    private String name;
    private Set<PermissionDto> permissions;
}
