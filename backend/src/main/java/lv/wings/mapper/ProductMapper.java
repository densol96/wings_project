package lv.wings.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import lv.wings.dto.request.admin.products.CreateProductTranslationDto;
import lv.wings.dto.response.ImageDto;
import lv.wings.dto.response.admin.common.TitleLocalableDto;
import lv.wings.dto.response.admin.products.ExistingProductDto;
import lv.wings.dto.response.admin.products.ExistingProductMaterialDto;
import lv.wings.dto.response.admin.products.ExistingProductTranslationDto;
import lv.wings.dto.response.admin.products.ProductAdminDto;
import lv.wings.dto.response.color.ColorDto;
import lv.wings.dto.response.product.ProductDto;
import lv.wings.dto.response.product.ProductMaterialDto;
import lv.wings.dto.response.product.ProductTitleDto;
import lv.wings.dto.response.product.ProductTranslationDto;
import lv.wings.dto.response.product.RandomProductDto;
import lv.wings.dto.response.product.SearchedProductDto;
import lv.wings.dto.response.product.ShortProductDto;
import lv.wings.dto.response.product.ShortProductTranslationDto;
import lv.wings.dto.response.product_category.ShortProductCategoryDto;
import lv.wings.model.entity.Color;
import lv.wings.model.entity.Product;
import lv.wings.model.translation.ProductTranslation;

@Mapper(componentModel = "spring", uses = {CommonMapper.class, UserMapper.class})
public interface ProductMapper {
        ProductTranslationDto toTranslationDto(ProductTranslation translated);

        ShortProductTranslationDto toShortTranslationDto(ProductTranslation translated);

        @Mapping(target = "id", source = "product.id")
        ShortProductDto toShortDto(Product product, ProductTranslation translationDto, List<ImageDto> imageDtos);

        @Mapping(target = "id", source = "product.id")
        ProductDto toDto(Product product,
                        ProductTranslation translationDto,
                        ShortProductCategoryDto categoryDto,
                        List<ImageDto> imageDtos,
                        List<ColorDto> colorDtos,
                        List<ProductMaterialDto> materialDtos);

        @Mapping(target = "id", source = "product.id")
        RandomProductDto toRandomDto(Product product, ProductTranslation translationDto, ShortProductCategoryDto categoryDto, List<ImageDto> imageDtos);

        @Mapping(target = "id", source = "product.id")
        SearchedProductDto toSearchedProductDto(Product product, ProductTranslation translationDto, ImageDto imageDto);

        @Mapping(target = "id", source = "product.id")
        ProductTitleDto toProductTitleDto(Product product, ProductTranslation translationDto);

        @Mapping(target = "translations", expression = "java(transform(product.getNarrowTranslations()))")
        @Mapping(target = "sold", expression = "java(getAmountSold(product))")
        ProductAdminDto toBaseAdmin(Product product);

        ProductTranslation dtoToTranslation(CreateProductTranslationDto dto);

        @Mapping(target = "categoryId", source = "product.category.id")
        @Mapping(target = "translations", expression = "java(transformToExisting(product.getNarrowTranslations()))")
        ExistingProductDto toExistingDto(Product product, List<ExistingProductMaterialDto> materials);

        ExistingProductTranslationDto toProductTranslationDto(ProductTranslation dto);

        default List<ExistingProductTranslationDto> transformToExisting(List<ProductTranslation> translations) {
                return translations.stream()
                                .map(this::toProductTranslationDto)
                                .toList();
        }

        default Integer getAmountSold(Product product) {
                return product.getOrderItems().stream().map(orderItem -> orderItem.getAmount()).reduce(0, (a, b) -> a + b);
        }



        default List<TitleLocalableDto> transform(List<ProductTranslation> translations) {
                return translations.stream().map(e -> new TitleLocalableDto(e.getLocale(), e.getTitle())).toList();
        }

        default Integer getColorId(Color productColor) {
                return productColor.getId();
        }

}
