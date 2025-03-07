package lv.wings.dto.response;

import java.util.Date;

import lombok.Builder;

@Builder
public class ShortEventDto {
    private Integer id;
    private Integer title;
    private Integer description;
    private Date startDate;
    private Date endDate;
    private String imageUrl;
}