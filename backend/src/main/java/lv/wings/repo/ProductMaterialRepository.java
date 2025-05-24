package lv.wings.repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import jakarta.transaction.Transactional;
import lv.wings.model.entity.ProductMaterial;

public interface ProductMaterialRepository extends JpaRepository<ProductMaterial, Integer> {

    List<ProductMaterial> findAllByProductId(Integer productId);

    @Modifying
    @Transactional
    void deleteAllByProductId(Integer productId);
}
