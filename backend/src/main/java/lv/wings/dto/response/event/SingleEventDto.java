package lv.wings.dto.response.event;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lv.wings.dto.response.ImageDto;

@Builder
@Getter
public class SingleEventDto {
    private Integer id;
    private LocalDateTime createdAt;
    private LocalDate startDate;
    private LocalDate endDate;
    private EventTranslationDto translation;
    private List<ImageDto> images;
}
