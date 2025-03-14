package lv.wings.dto.response.product_category;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ProductCategoryDto {
    private String title;
    private String description;
}
