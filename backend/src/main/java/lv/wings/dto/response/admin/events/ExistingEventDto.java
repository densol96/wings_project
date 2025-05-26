package lv.wings.dto.response.admin.events;

import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExistingEventDto {
    private Integer id;
    private List<ExistingEventTranslationDto> translations;
    private LocalDate startDate;
    private LocalDate endDate;
}
