package lv.wings.repo;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import lv.wings.model.entity.Product;
import lv.wings.model.entity.ProductCategory;

public interface ProductRepository extends JpaRepository<Product, Integer> {

	// Product findByTitle(String title);

	List<Product> findByCategory(ProductCategory category);

	Page<Product> findAllByCategoryId(Integer categoryId, Pageable pageable);

	@Query(value = "SELECT * FROM product ORDER BY RAND() LIMIT :count", nativeQuery = true)
	List<Product> findRandomProducts(@Param("count") int count);

}
