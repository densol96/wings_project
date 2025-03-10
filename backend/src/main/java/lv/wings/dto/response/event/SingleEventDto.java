package lv.wings.dto.response.event;

import java.time.LocalDate;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lv.wings.dto.response.ImageDto;

@Builder
@Getter
public class SingleEventDto {
    private Integer id;
    private LocalDate startDate;
    private LocalDate endDate;
    private EventTranslationDto translation;
    private List<ImageDto> images;
}
