package lv.wings.dto.response.admin.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lv.wings.enums.LocaleCode;

@Getter
@Setter
@AllArgsConstructor
public class TitleLocalableDto {
    private LocaleCode locale;
    private String title;
}
