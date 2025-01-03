package lv.wings.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import lv.wings.model.ProductCategory;

public interface IProductCategoryRepo extends CrudRepository<ProductCategory, Integer>, PagingAndSortingRepository<ProductCategory, Integer>{

	ProductCategory findByTitleAndDescription(String title, String description);

}
