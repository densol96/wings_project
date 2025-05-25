package lv.wings.dto.request.admin.products;

import java.util.List;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lv.wings.dto.interfaces.HasTranslationMethod;
import lv.wings.enums.TranslationMethod;

@Getter
@Setter
public class NewCategoryDto implements HasTranslationMethod {

    private TranslationMethod translationMethod;

    @NotEmpty(message = "{translations.empty}")
    @Valid
    private List<CreateCategoryTranslationDto> translations;
}
