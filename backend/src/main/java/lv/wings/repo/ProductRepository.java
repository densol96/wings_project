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

	@Query("""
			    SELECT DISTINCT p
			    FROM Product p
			    JOIN p.translations t
			    WHERE (:categoryId = 0 OR p.category.id = :categoryId)
			      AND LOWER(t.title) LIKE LOWER(CONCAT('%', :q, '%'))
			""")
	Page<Product> searchByCategoryAndTitle(@Param("q") String q, @Param("categoryId") Integer categoryId, Pageable pageable);


	@Query(
			value = """
					SELECT p.*, COALESCE(SUM(oi.amount), 0) AS sold
					FROM products p
					LEFT JOIN order_items oi ON oi.product_id = p.id
					LEFT JOIN product_translations t ON t.translatable_id = p.id
					WHERE (:categoryId = 0 OR p.product_category_id = :categoryId)
					  AND LOWER(t.title) LIKE LOWER(CONCAT('%', :q, '%'))
					GROUP BY p.id
					ORDER BY
					    CASE WHEN :direction = 'asc' THEN sold END ASC,
					    CASE WHEN :direction = 'desc' THEN sold END DESC
					""",
			nativeQuery = true)
	Page<Product> findWithSoldSortedNative(
			@Param("q") String q,
			@Param("categoryId") Integer categoryId,
			@Param("direction") String direction,
			Pageable pageable);

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
