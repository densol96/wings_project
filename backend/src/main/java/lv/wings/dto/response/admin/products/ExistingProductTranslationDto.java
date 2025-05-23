package lv.wings.dto.response.admin.products;

import lombok.Getter;
import lombok.Setter;
import lv.wings.enums.LocaleCode;

@Getter
@Setter
public class ExistingProductTranslationDto {
    private LocaleCode locale;
    private String title;
    private String description;
}
