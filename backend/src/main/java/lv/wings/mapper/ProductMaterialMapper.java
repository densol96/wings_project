package lv.wings.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import lv.wings.dto.response.admin.products.ExistingProductMaterialDto;
import lv.wings.dto.response.product.MaterialDto;
import lv.wings.dto.response.product.ProductMaterialDto;
import lv.wings.model.entity.ProductMaterial;

@Mapper(componentModel = "spring")
public interface ProductMaterialMapper {
    @Mapping(target = "material", source = "materialDto")
    ProductMaterialDto toDto(ProductMaterial productMaterial, MaterialDto materialDto);

    @Mapping(target = "id", source = "productMaterial.material.id")
    ExistingProductMaterialDto toExistingDto(ProductMaterial productMaterial, String name);
}
