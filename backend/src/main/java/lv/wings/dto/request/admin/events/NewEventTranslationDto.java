package lv.wings.dto.request.admin.events;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lv.wings.enums.LocaleCode;
import lv.wings.model.interfaces.LocalableWithTitle;

@Getter
@Setter
public class NewEventTranslationDto implements LocalableWithTitle {
    @NotNull(message = "{locale.required}")
    private LocaleCode locale;

    @NotBlank(message = "{title.required}")
    @Size(min = 5, max = 100, message = "{title.size}")
    private String title;

    @NotBlank(message = "{description.required}")
    @Size(min = 50, max = 1000, message = "{description.size}")
    private String description;

    @Size(min = 5, max = 50, message = "{location.size}")
    private String location;
}
