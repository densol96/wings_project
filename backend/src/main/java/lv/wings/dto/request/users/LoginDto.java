package lv.wings.dto.request.users;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDto {
    @NotBlank(message = "{username.required}")
    @Size(min = 3, max = 20, message = "{username.size}")
    private String username;

    @NotBlank(message = "{password.required}")
    @Size(min = 6, max = 50, message = "{password.size}")
    private String password;
}
