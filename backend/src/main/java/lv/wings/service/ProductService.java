package lv.wings.service;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import lv.wings.dto.response.product.ShortProductDto;
import lv.wings.model.entity.Product;

public interface ProductService extends CRUDService<Product, Integer> {
    ShortProductDto getAll(Pageable pageable);
}
