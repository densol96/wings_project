package lv.wings.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ImageDto {
    private String src;
    private String alt;
}
