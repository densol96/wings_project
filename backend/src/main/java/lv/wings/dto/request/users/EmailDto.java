package lv.wings.dto.request.users;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailDto {
    @Email(message = "{email.invalid}")
    @NotBlank(message = "{email.required}")
    private String email;
}
