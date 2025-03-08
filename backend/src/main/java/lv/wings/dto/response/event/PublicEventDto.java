package lv.wings.dto.response.event;

import java.util.Date;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PublicEventDto {
    private Integer id;
    private Date startDate;
    private Date endDate;
    private String imageUrl;
    private String category;
    private EventTranslationDto translation;
}