package lv.wings.repo;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import lv.wings.model.Product;
import lv.wings.model.ProductCategory;

public interface IProductRepo extends CrudRepository<Product, Integer>, PagingAndSortingRepository<Product, Integer> {

	Product findByTitle(String title);

	ArrayList<Product> findByProductCategory(ProductCategory productCategory);

	@Query(value = "SELECT * FROM product ORDER BY RAND() LIMIT :count", nativeQuery = true)
	ArrayList<Product> findRandomProducts(@Param("count") int count);

}
