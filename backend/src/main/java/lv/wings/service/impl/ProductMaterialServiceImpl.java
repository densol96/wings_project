package lv.wings.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import io.micrometer.common.lang.NonNull;
import lv.wings.dto.response.admin.products.ExistingProductMaterialDto;
import lv.wings.dto.response.product.ProductMaterialDto;
import lv.wings.enums.LocaleCode;
import lv.wings.mapper.ProductMaterialMapper;
import lv.wings.model.entity.Product;
import lv.wings.model.entity.ProductMaterial;
import lv.wings.repo.ProductMaterialRepository;
import lv.wings.service.AbstractCRUDService;
import lv.wings.service.MaterialService;
import lv.wings.service.ProductMaterialService;
import lv.wings.util.CustomValidator;

@Service
public class ProductMaterialServiceImpl extends AbstractCRUDService<ProductMaterial, Integer> implements ProductMaterialService {

    private final ProductMaterialRepository productMaterialRepo;
    private final MaterialService materialService;
    private final ProductMaterialMapper productMaterialMapper;

    public ProductMaterialServiceImpl(ProductMaterialRepository repository, MaterialService materialService, ProductMaterialMapper productMaterialMapper) {
        super(repository, "Product Material", "entity.product-material");
        this.productMaterialRepo = repository;
        this.materialService = materialService;
        this.productMaterialMapper = productMaterialMapper;
    }

    @Override
    public List<ProductMaterialDto> getProductMaterialsPerProductId(Integer productId) {
        CustomValidator.isValidId("product id", productId);
        return productMaterialRepo.findAllByProductId(productId).stream().map(this::mapToProductMaterialDto).toList();
    }

    @Override
    public List<ExistingProductMaterialDto> getMaterialsPerProduct(@NonNull Product product, @NonNull LocaleCode locale) {
        return product.getMadeOfMaterials()
                .stream()
                .map((pm) -> productMaterialMapper.toExistingDto(pm, materialService.getSelectedTranslation(pm.getMaterial(), locale).getName()))
                .toList();

    }

    private ProductMaterialDto mapToProductMaterialDto(ProductMaterial productMaterial) {
        return productMaterialMapper.toDto(productMaterial, materialService.mapToDto(productMaterial.getMaterial()));
    }

    @Override
    public void deleteProductMaterialsPerProductId(@NonNull Integer productId) {
        productMaterialRepo.deleteAllByProductId(productId);
    }


}
