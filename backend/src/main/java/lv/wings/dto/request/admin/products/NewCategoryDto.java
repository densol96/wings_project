package lv.wings.dto.request.admin.products;

import java.util.List;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewCategoryDto {
    @NotEmpty(message = "{translations.empty}")
    @Valid
    private List<CreateProductTranslationDto> translations;
}
