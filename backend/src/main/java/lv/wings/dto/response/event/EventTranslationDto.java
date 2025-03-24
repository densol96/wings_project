package lv.wings.dto.response.event;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class EventTranslationDto {
    private String title;
    private String description;
    private String location;
}
