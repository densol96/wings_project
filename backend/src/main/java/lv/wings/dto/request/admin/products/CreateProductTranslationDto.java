package lv.wings.dto.request.admin.products;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lv.wings.enums.LocaleCode;
import lv.wings.model.interfaces.Localable;

@Getter
@Setter
public class CreateProductTranslationDto implements Localable {
    @NotNull(message = "{locale.required}")
    private LocaleCode locale;

    @NotBlank(message = "{title.required}")
    @Size(min = 5, max = 50, message = "{title.size}")
    private String title;

    @Size(min = 10, max = 1000, message = "{description.size}")
    private String description;
}
