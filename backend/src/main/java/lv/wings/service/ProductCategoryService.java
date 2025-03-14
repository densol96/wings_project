package lv.wings.service;

import java.util.List;

import lv.wings.dto.response.product_category.ProductCategoryDto;
import lv.wings.dto.response.product_category.ProductCategoryWithAmountDto;

public interface ProductCategoryService {

    List<ProductCategoryWithAmountDto> getAllCategories();

    ProductCategoryDto getCategory(Integer id);
}
