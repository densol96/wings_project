package lv.wings.dto.request.users;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordDto {
    @NotBlank(message = "{password.required}")
    @Size(min = 6, max = 50, message = "{password.size}")
    private String password;

    @NotBlank(message = "{confirm-password.required}")
    @Size(min = 6, max = 50, message = "{confirm-password.size}")
    private String confirmPassword;

    @NotBlank(message = "{old-password.required}")
    @Size(min = 6, max = 50, message = "{old-password.size}")
    private String oldPassword;
}
