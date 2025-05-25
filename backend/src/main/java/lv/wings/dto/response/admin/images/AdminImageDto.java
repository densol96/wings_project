package lv.wings.dto.response.admin.images;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lv.wings.dto.response.admin.orders.UserMinDto;

@Getter
@Setter
@Builder
public class AdminImageDto {
    private Integer id;
    private String src;
    private String alt;
    private UserMinDto createdBy;
    private LocalDateTime createdAt;
}
