package lv.wings.repo;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import lv.wings.model.Product;
import lv.wings.model.ProductCategory;

public interface IProductRepo extends CrudRepository<Product, Integer>, PagingAndSortingRepository<Product, Integer>{

	Product findByTitle(String title);
	
	ArrayList<Product> findByProductCategory(ProductCategory productCategory);

}
