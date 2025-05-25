package lv.wings.repo.base;

import org.springframework.data.repository.NoRepositoryBean;
import lv.wings.enums.LocaleCode;

@NoRepositoryBean
public interface TitleWithLocaleAndSoftDeleteRepository<T> extends SearchableRepository<T> {
    boolean existsByTitleAndLocaleAndDeletedFalse(String title, LocaleCode locale);
}
