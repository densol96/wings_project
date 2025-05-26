package lv.wings.dto.response.admin.events;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import lv.wings.enums.LocaleCode;

@Getter
@Setter
@Builder
public class ExistingEventTranslationDto {
    private LocaleCode locale;
    private String title;
    private String description;
    private String location;
}
