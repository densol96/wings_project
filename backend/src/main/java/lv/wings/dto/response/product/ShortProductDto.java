package lv.wings.dto.response.product;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ShortProductDto {
    private Integer id;
    private String title;
    private Double price;
    private Integer amount;
}
