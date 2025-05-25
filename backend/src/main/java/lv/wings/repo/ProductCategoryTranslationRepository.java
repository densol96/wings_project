package lv.wings.repo;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import io.lettuce.core.dynamic.annotation.Param;
import jakarta.transaction.Transactional;
import lv.wings.model.translation.ProductCategoryTranslation;
import lv.wings.repo.base.TitleWithLocaleAndSoftDeleteRepository;

public interface ProductCategoryTranslationRepository extends TitleWithLocaleAndSoftDeleteRepository<ProductCategoryTranslation> {
    @Modifying
    @Transactional
    @Query("DELETE FROM ProductCategoryTranslation pct WHERE pct.entity.id = :productCategoryId")
    void hardDeleteByCategoryId(@Param("productId") Integer productCategoryId);
}
