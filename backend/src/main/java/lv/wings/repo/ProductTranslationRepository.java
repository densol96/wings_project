package lv.wings.repo;


import lv.wings.enums.LocaleCode;
import lv.wings.model.translation.ProductTranslation;
import lv.wings.repo.base.SearchableRepository;

public interface ProductTranslationRepository extends SearchableRepository<ProductTranslation> {
    boolean existsByTitleAndLocaleAndDeletedFalse(String title, LocaleCode locale);
}
