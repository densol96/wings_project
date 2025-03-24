package lv.wings.dto.response.event;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lv.wings.dto.response.ImageDto;

@Builder
@Getter
@Setter
public class ShortEventDto {
    private Integer id;
    private LocalDateTime createdAt;
    private ShortEventTranslationDto translation;
    private ImageDto image;
}
