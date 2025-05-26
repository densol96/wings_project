package lv.wings.dto.response.admin.events;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lv.wings.dto.response.admin.common.TitleLocalableDto;
import lv.wings.dto.response.admin.orders.UserMinDto;

@Getter
@Setter
public class EventAdminDto {
    private Integer id;
    private List<TitleLocalableDto> translations;
    private LocalDate startDate;
    private LocalDate endDate;
    private UserMinDto createdBy;
    private LocalDateTime createdAt;
    private UserMinDto lastModifiedBy;
    private LocalDateTime lastModifiedAt;
}
