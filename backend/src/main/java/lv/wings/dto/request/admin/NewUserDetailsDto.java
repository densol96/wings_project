package lv.wings.dto.request.admin;

import java.util.List;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lv.wings.dto.interfaces.HasEmailAndUsername;

@Getter
@Setter
@ToString
public class NewUserDetailsDto implements HasEmailAndUsername {
    @NotBlank(message = "{username.required}")
    @Size(min = 3, max = 20, message = "{username.size}")
    @Pattern(
            regexp = "^[^\\s.,!?@#\\$%\\^&*()+=\\[\\]{}:;\"'\\\\/<>|`~]+$",
            message = "{username.pattern}")
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

    private List<Integer> roles;
}
