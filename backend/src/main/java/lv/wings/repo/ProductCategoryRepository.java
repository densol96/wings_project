package lv.wings.repo;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import lv.wings.model.entity.ProductCategory;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {
    Optional<ProductCategory> findByIdAndDeletedFalse(Integer id);

    List<ProductCategory> findAll(Sort sort);
}
