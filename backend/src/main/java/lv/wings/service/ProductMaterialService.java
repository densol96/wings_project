package lv.wings.service;

import java.util.List;
import lv.wings.dto.response.product.ProductMaterialDto;

public interface ProductMaterialService {
    List<ProductMaterialDto> getProductMaterialsPerProductId(Integer productId);
}
