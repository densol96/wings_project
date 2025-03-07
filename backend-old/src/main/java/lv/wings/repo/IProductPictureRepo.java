package lv.wings.repo;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import lv.wings.model.Product;
import lv.wings.model.ProductPicture;

public interface IProductPictureRepo extends CrudRepository<ProductPicture,Integer>, PagingAndSortingRepository<ProductPicture, Integer>{

	ProductPicture findByReferenceToPicture(String reference);
	
	ArrayList<ProductPicture> findByProduct(Product product);

}
