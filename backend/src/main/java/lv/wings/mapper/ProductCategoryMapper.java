package lv.wings.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import lv.wings.dto.response.product_category.ProductCategoryDto;
import lv.wings.dto.response.product_category.ProductCategoryWithAmountDto;
import lv.wings.dto.response.product_category.ShortProductCategoryDto;
import lv.wings.model.entity.ProductCategory;
import lv.wings.model.translation.ProductCategoryTranslation;

@Mapper(componentModel = "spring")
public interface ProductCategoryMapper {

    ProductCategoryDto translationToDto(ProductCategoryTranslation productCategoryTranslation);

    @Mapping(target = "id", source = "category.id")
    ShortProductCategoryDto translationToShortDto(ProductCategory category, ProductCategoryTranslation translation);

    @Mapping(target = "id", source = "category.id")
    @Mapping(target = "productsTotal", expression = "java((long) category.getProducts().size())")
    ProductCategoryWithAmountDto toWithAmountDto(ProductCategory category, ProductCategoryTranslation translation);
}
