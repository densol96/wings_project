package lv.wings.dto.response.product;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lv.wings.dto.response.ImageDto;
import lv.wings.dto.response.product_category.ShortProductCategoryDto;

@Builder
@Getter
@Setter
public class ShortProductDto {
    private Integer id;
    private Double price;
    private Integer amount;
    private List<ImageDto> images;
    private ShortProductTranslationDto translation;
}
