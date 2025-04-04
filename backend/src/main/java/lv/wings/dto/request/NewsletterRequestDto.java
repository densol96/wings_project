package lv.wings.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class NewsletterRequestDto {
    @NotBlank(message = "{email.required}")
    @Email(message = "{email.invalid}")
    private String email;
}
