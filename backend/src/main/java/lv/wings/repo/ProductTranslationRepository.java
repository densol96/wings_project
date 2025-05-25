package lv.wings.repo;


import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import io.lettuce.core.dynamic.annotation.Param;
import jakarta.transaction.Transactional;
import lv.wings.model.translation.ProductTranslation;
import lv.wings.repo.base.TitleWithLocaleAndSoftDeleteRepository;

public interface ProductTranslationRepository extends TitleWithLocaleAndSoftDeleteRepository<ProductTranslation> {
    @Modifying
    @Transactional
    @Query("DELETE FROM ProductTranslation pt WHERE pt.entity.id = :productId")
    void hardDeleteByProductId(@Param("productId") Integer productId);
}
