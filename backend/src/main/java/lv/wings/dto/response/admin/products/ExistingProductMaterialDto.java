package lv.wings.dto.response.admin.products;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ExistingProductMaterialDto {
    private Integer id;
    private Integer percentage;
    private String name;

}
