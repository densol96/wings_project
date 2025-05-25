package lv.wings.dto.response.admin.products;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lv.wings.enums.LocaleCode;

@Getter
@Setter
@Builder
public class ExistingCategoryTranslationDto {
    private LocaleCode locale;
    private String title;
    private String description;
}
