package lv.wings.repo.base;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import lv.wings.enums.LocaleCode;

@NoRepositoryBean
public interface SearchableRepository<T> extends JpaRepository<T, Integer> {
    List<T> findByTitleContainingIgnoreCaseAndLocaleEquals(String keyword, LocaleCode locale);
}
