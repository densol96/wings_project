package lv.wings.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import lv.wings.dto.response.product.ProductDto;
import lv.wings.dto.response.product.RandomProductDto;
import lv.wings.dto.response.product.SearchedProductDto;
import lv.wings.dto.response.product.ShortProductDto;
import lv.wings.model.entity.Product;

public interface ProductService extends CRUDService<Product, Integer> {
    Page<ShortProductDto> getAllByCategory(Integer categoryId, Pageable pageable);

    ProductDto getProductById(Integer id);

    List<RandomProductDto> getRandomProducts(Integer categoryId, Integer amount);

    List<SearchedProductDto> getSearchedProducts(String q);
}
