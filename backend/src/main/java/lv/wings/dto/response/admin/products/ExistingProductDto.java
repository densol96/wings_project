package lv.wings.dto.response.admin.products;

import java.math.BigDecimal;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExistingProductDto {
    private Integer id;
    private BigDecimal price;
    private Integer amount;
    private Integer categoryId;
    private List<ExistingProductTranslationDto> translations;
    private List<Integer> colors;
    private List<ExistingProductMaterialDto> materials;

}
