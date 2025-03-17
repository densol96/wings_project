package lv.wings.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import lv.wings.model.entity.Product;
import lv.wings.model.entity.ProductImage;


public interface ProductPictureRepository extends JpaRepository<ProductImage, Integer> {

	// ProductPicture findByReferenceToPicture(String reference);

	List<ProductImage> findByProduct(Product product);

}
