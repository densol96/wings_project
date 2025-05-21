package lv.wings.service;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import lombok.NonNull;
import lv.wings.dto.response.admin.products.ProductAdminDto;
import lv.wings.dto.response.product.ProductDto;
import lv.wings.dto.response.product.ProductTitleDto;
import lv.wings.dto.response.product.RandomProductDto;
import lv.wings.dto.response.product.SearchedProductDto;
import lv.wings.dto.response.product.ShortProductDto;
import lv.wings.enums.LocaleCode;
import lv.wings.model.entity.Product;
import lv.wings.model.translation.ProductTranslation;

public interface ProductService extends CRUDService<Product, Integer> {
    Page<ShortProductDto> getAllByCategory(Integer categoryId, Pageable pageable);

    Page<ProductAdminDto> getAllByCategoryForAdmin(String q, Integer categoryId, Pageable pageable);

    ProductDto getProductById(Integer id);

    List<RandomProductDto> getRandomProducts(Integer categoryId, Integer amount);

    List<SearchedProductDto> getSearchedProducts(String q);

    List<ProductTitleDto> getProductDtosByIds(List<Integer> ids);

    List<Product> getProductsByIds(List<Integer> ids);

    List<Product> getProductsByIdsWithLock(@NonNull List<Integer> ids);

    Optional<Product> getProductByIdWithLock(Integer id);

    ProductTranslation getRightTranslation(Product product);

    ProductTranslation getSelectedTranslation(Product product, LocaleCode localeCode);
}
