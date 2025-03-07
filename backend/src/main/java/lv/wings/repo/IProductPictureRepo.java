package lv.wings.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import lv.wings.model.Product;
import lv.wings.model.ProductPicture;

public interface IProductPictureRepo extends JpaRepository<ProductPicture, Integer> {

	ProductPicture findByReferenceToPicture(String reference);

	List<ProductPicture> findByProduct(Product product);

}
