package lv.wings.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import lv.wings.model.ProductCategory;

public interface IProductCategoryRepo extends JpaRepository<ProductCategory, Integer> {

	ProductCategory findByTitleAndDescription(String title, String description);

}
