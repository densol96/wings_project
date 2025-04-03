package lv.wings.dto.response.product;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ProductMaterialDto {
    private MaterialDto material;
    private Integer percentage;
}
