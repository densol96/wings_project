package lv.wings.dto.response.admin.orders;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class StatusDto {
    private String code;
    private String name;
}
