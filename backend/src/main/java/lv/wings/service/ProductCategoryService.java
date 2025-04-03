package lv.wings.service;

import java.util.List;

import lv.wings.dto.response.product_category.ProductCategoryDto;
import lv.wings.dto.response.product_category.ProductCategoryWithAmountDto;
import lv.wings.dto.response.product_category.ShortProductCategoryDto;
import lv.wings.model.entity.ProductCategory;

public interface ProductCategoryService {

    List<ProductCategoryWithAmountDto> getAllCategories();

    ProductCategoryDto getCategory(Integer id);

    ShortProductCategoryDto getShortCategory(Integer id);

    ShortProductCategoryDto mapToShortDto(ProductCategory category);
}
