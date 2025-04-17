package lv.wings.repo;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import lv.wings.model.entity.Product;
import lv.wings.model.entity.ProductCategory;

public interface ProductRepository extends JpaRepository<Product, Integer> {

	List<Product> findByCategory(ProductCategory category);

	Page<Product> findAllByCategoryId(Integer categoryId, Pageable pageable);

	// This should work in MySQL but wil need to be changed for other DBMSs
	@Query(value = "SELECT * FROM products WHERE amount > 0 ORDER BY RAND() LIMIT :amount", nativeQuery = true)
	List<Product> findAvaialableRandomProductsFromAll(@Param("amount") Integer amount);

	@Query(value = "SELECT * FROM products WHERE product_category_id = :productCategoryId and amount > 0 ORDER BY RAND() LIMIT :amount", nativeQuery = true)
	List<Product> findAvaialableRandomProductsByCategory(@Param("productCategoryId") Integer productCategoryId, @Param("amount") Integer amount);

	@Query(value = "SELECT * FROM products WHERE amount = 0 ORDER BY RAND() LIMIT :amount", nativeQuery = true)
	List<Product> findUnavaialableRandomProductsFromAll(@Param("amount") Integer amount);

	@Query(value = "SELECT * FROM products WHERE product_category_id = :productCategoryId and amount = 0 ORDER BY RAND() LIMIT :amount", nativeQuery = true)
	List<Product> findUnavaialableRandomProductsByCategory(@Param("productCategoryId") Integer productCategoryId, @Param("amount") Integer amount);

	@Query(value = "SELECT * from products WHERE id IN :ids FOR UPDATE", nativeQuery = true)
	List<Product> getProductsByIdsWithLock(List<Integer> ids);

	@Query(value = "SELECT * from products WHERE id = :id FOR UPDATE", nativeQuery = true)
	Optional<Product> getProductByIdWithLock(Integer id);
}
