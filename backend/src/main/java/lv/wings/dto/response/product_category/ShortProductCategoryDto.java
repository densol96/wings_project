package lv.wings.dto.response.product_category;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ShortProductCategoryDto {
    private Integer id;
    private String title;
}
