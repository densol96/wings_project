package lv.wings.repo;


import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import io.lettuce.core.dynamic.annotation.Param;
import jakarta.transaction.Transactional;
import lv.wings.enums.LocaleCode;
import lv.wings.model.translation.ProductTranslation;
import lv.wings.repo.base.SearchableRepository;

public interface ProductTranslationRepository extends SearchableRepository<ProductTranslation> {
    boolean existsByTitleAndLocaleAndDeletedFalse(String title, LocaleCode locale);


    @Modifying
    @Transactional
    @Query("DELETE FROM ProductTranslation pt WHERE pt.entity.id = :productId")
    void hardDeleteByProductId(@Param("productId") Integer productId);
}
