package lv.wings.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import lv.wings.dto.response.admin.common.TitleLocalableDto;
import lv.wings.dto.response.admin.products.AdminProductCategoryDto;
import lv.wings.dto.response.admin.products.EditCategoryDto;
import lv.wings.dto.response.admin.products.ExistingCategoryTranslationDto;
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
    @Mapping(target = "productsTotal", expression = "java((long) category.getProducts().stream().filter(pr -> !pr.isDeleted()).toList().size())")
    ProductCategoryWithAmountDto toWithAmountDto(ProductCategory category, ProductCategoryTranslation translation);

    @Mapping(target = "id", source = "category.id")
    @Mapping(target = "translations", expression = "java(transform(category.getNarrowTranslations()))")
    @Mapping(target = "productsTotal", expression = "java(category.getProducts().stream().filter(pr -> !pr.isDeleted()).toList().size())")
    AdminProductCategoryDto toAdminDto(ProductCategory category);

    @Mapping(target = "translations", source = "narrowTranslations")
    EditCategoryDto toEditDto(ProductCategory category);

    ExistingCategoryTranslationDto toExistingTranslation(ProductCategoryTranslation translation);

    default List<TitleLocalableDto> transform(List<ProductCategoryTranslation> translations) {
        return translations.stream().map(e -> new TitleLocalableDto(e.getLocale(), e.getTitle())).toList();
    }
}
