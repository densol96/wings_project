package lv.wings.service;

import java.util.List;
import org.springframework.data.domain.Sort;
import lv.wings.dto.request.admin.products.NewCategoryDto;
import lv.wings.dto.response.BasicMessageDto;
import lv.wings.dto.response.admin.products.AdminProductCategoryDto;
import lv.wings.dto.response.admin.products.EditCategoryDto;
import lv.wings.dto.response.admin.products.ExistingProductDto;
import lv.wings.dto.response.product_category.ProductCategoryDto;
import lv.wings.dto.response.product_category.ProductCategoryWithAmountDto;
import lv.wings.dto.response.product_category.ShortProductCategoryDto;
import lv.wings.model.entity.ProductCategory;

public interface ProductCategoryService extends CRUDService<ProductCategory, Integer> {

    List<ProductCategoryWithAmountDto> getAllCategories();

    ShortProductCategoryDto getShortCategory(Integer id);

    ShortProductCategoryDto mapToShortDto(ProductCategory category);

    ProductCategoryDto getCategory(Integer id);

    BasicMessageDto createCategory(NewCategoryDto dto);

    BasicMessageDto updateCategory(NewCategoryDto dto, Integer id);

    BasicMessageDto deleteCategory(Integer id);

    ProductCategory findByIdAndNotDeleted(Integer id);

    List<AdminProductCategoryDto> getAllAdminCategories(Sort sort);

    EditCategoryDto getExistingCategoryForAdmin(Integer id);
}
