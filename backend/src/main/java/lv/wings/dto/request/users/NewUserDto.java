package lv.wings.dto.request.users;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewUserDto {
    @Email(message = "{email.invalid}")
    @NotBlank(message = "{email.required}")
    private String email;

    @NotBlank(message = "{username.required}")
    @Size(min = 3, max = 20, message = "{username.size}")
    private String username;

    @NotBlank(message = "{password.required}")
    @Size(min = 6, max = 50, message = "{password.size}")
    private String password;

    @NotBlank(message = "{firstName.required}")
    @Pattern(
            regexp = "^[A-Za-zĀ-ž\\-\\s]{2,50}$",
            message = "{firstName.invalid}")
    private String firstName;

    @NotBlank(message = "{lastName.required}")
    @Pattern(
            regexp = "^[A-Za-zĀ-ž\\-\\s]{2,50}$",
            message = "{lastName.invalid}")
    private String lastName;
}
