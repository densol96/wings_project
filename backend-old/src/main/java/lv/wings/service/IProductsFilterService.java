package lv.wings.service;

import java.util.ArrayList;

import lv.wings.model.Product;

public interface IProductsFilterService {
    ArrayList<Product> selectAllByProductCategory(int categoryId) throws Exception;

    ArrayList<Product> randomProducts() throws Exception;
}
