package lv.wings.repo;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import lv.wings.model.ProductCategory;
import lv.wings.model.Product;

public interface IProductRepo extends CrudRepository<Product, Integer>{

	Product findByTitle(String title);
	
	ArrayList<Product> findByProductCategory(ProductCategory productCategory);

}
