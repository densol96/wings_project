package lv.wings.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BasicErrorDto {
    private String message;
}
