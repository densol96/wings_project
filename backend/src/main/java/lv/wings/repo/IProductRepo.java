package lv.wings.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import lv.wings.model.Product;
import lv.wings.model.ProductCategory;

public interface IProductRepo extends JpaRepository<Product, Integer> {

	Product findByTitle(String title);

	List<Product> findByProductCategory(ProductCategory productCategory);

	@Query(value = "SELECT * FROM product ORDER BY RAND() LIMIT :count", nativeQuery = true)
	List<Product> findRandomProducts(@Param("count") int count);

}
