package lv.wings.dto.response.event;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lv.wings.dto.response.ImageDto;

@Builder
@Getter
@Setter
public class SearchedEventDto {
    private Integer id;
    private String title;
    private LocalDateTime createdAt;
    private ImageDto imageDto;
}
