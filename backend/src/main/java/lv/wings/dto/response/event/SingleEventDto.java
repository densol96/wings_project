package lv.wings.dto.response.event;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SingleEventDto {
    private Integer id;
    private LocalDate startDate;
    private LocalDate endDate;
    private String imageUrl;
}
