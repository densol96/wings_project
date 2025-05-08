package lv.wings.dto.request.admin;

import java.util.List;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewRoleDto {
    @NotBlank(message = "{role.name.required}")
    @Size(max = 100, message = "{role.name.size}")
    private String name;

    @NotBlank(message = "{permissions.required}")
    private List<String> permissionCodes;
}
