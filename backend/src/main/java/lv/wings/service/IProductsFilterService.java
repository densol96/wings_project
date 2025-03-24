package lv.wings.service;

import java.util.List;

import lv.wings.model.entity.Product;

public interface IProductsFilterService {
    List<Product> selectAllByProductCategory(Integer categoryId) throws Exception;

    List<Product> randomProducts() throws Exception;
}
