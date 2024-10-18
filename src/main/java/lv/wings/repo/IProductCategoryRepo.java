package lv.wings.repo;

import org.springframework.data.repository.CrudRepository;

import lv.wings.model.ProductCategory;

public interface IProductCategoryRepo extends CrudRepository<ProductCategory, Integer>{

	ProductCategory findByTitleAndDescription(String title, String description);

}
