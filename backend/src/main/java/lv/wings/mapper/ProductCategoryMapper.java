package lv.wings.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import lv.wings.dto.response.product_category.ProductCategoryDto;
import lv.wings.dto.response.product_category.ProductCategoryWithAmountDto;
import lv.wings.model.entity.ProductCategory;
import lv.wings.model.translation.ProductCategoryTranslation;

@Mapper(componentModel = "spring")
public interface ProductCategoryMapper {

    ProductCategoryDto translationToDto(ProductCategoryTranslation productCategoryTranslation);

    @Mapping(target = "productsTotal", expression = "java((long) category.getProducts().size())")
    ProductCategoryWithAmountDto toWithAmountDto(ProductCategory category, String title);
}
