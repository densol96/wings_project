package lv.wings.service;

import java.util.List;

import lv.wings.dto.response.product_category.ProductCategoryDto;
import lv.wings.dto.response.product_category.ProductCategoryWithAmountDto;
import lv.wings.dto.response.product_category.ShortProductCategoryDto;
import lv.wings.model.entity.ProductCategory;

public interface ProductCategoryService extends CRUDService<ProductCategory, Integer> {

    List<ProductCategoryWithAmountDto> getAllCategories();

    ShortProductCategoryDto getShortCategory(Integer id);

    ShortProductCategoryDto mapToShortDto(ProductCategory category);

    ProductCategoryDto getCategory(Integer id);
}
