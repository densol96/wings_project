package lv.wings.dto.response.product;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lv.wings.dto.response.ImageDto;

@Builder
@Getter
@Setter
public class SearchedProductDto {
    private Integer id;
    private Double price;
    private Integer amount;
    private ImageDto imageDto;
    private String title;
}
