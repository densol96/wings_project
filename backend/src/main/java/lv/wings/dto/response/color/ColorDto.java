package lv.wings.dto.response.color;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ColorDto {
    private Integer id;
    private String name;
}
