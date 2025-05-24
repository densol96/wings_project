package lv.wings.service;

import java.util.List;
import lv.wings.dto.response.admin.products.ExistingProductMaterialDto;
import lv.wings.dto.response.product.ProductMaterialDto;
import lv.wings.enums.LocaleCode;
import lv.wings.model.entity.Product;
import lv.wings.model.entity.ProductMaterial;

public interface ProductMaterialService extends CRUDService<ProductMaterial, Integer> {
    List<ProductMaterialDto> getProductMaterialsPerProductId(Integer productId);

    List<ExistingProductMaterialDto> getMaterialsPerProduct(Product product, LocaleCode locale);

    void deleteProductMaterialsPerProductId(Integer productId);
}
