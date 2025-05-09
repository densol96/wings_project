package lv.wings.dto.response.admin.roles;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PermissionDto {
    private Integer id;
    private String name;
    private String label;
}
