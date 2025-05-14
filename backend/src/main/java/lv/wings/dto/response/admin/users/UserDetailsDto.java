package lv.wings.dto.response.admin.users;

import java.util.List;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDetailsDto {

    @NotBlank(message = "{username.required}")
    @Size(min = 3, max = 20, message = "{username.size}")
    private String username;

    @Email(message = "{email.invalid}")
    @NotBlank(message = "{email.required}")
    private String email;

    @NotBlank(message = "{firstName.required}")
    @Size(max = 50, message = "{firstName.size}")
    private String firstName;

    @NotBlank(message = "{lastName.required}")
    @Size(max = 50, message = "{lastName.size}")
    private String lastName;

    @NotNull(message = "{accountLocked.required}")
    private Boolean accountLocked;

    @NotNull(message = "{accountBanned.required}")
    private Boolean accountBanned;

    private List<Integer> roles;
}
