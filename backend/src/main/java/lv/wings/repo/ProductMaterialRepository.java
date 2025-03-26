package lv.wings.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import lv.wings.model.entity.ProductMaterial;

public interface ProductMaterialRepository extends JpaRepository<ProductMaterial, Integer> {
}
