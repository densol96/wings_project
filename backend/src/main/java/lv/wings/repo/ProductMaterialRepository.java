package lv.wings.repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import lv.wings.model.entity.ProductMaterial;

public interface ProductMaterialRepository extends JpaRepository<ProductMaterial, Integer> {

    List<ProductMaterial> findAllByProductId(Integer productId);
}
