package lv.wings.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import lv.wings.dto.response.ImageDto;
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
import lv.wings.model.entity.Product;
import lv.wings.model.translation.ProductTranslation;

@Mapper(componentModel = "spring")
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
}
