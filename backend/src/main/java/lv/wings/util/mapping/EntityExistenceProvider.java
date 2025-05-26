package lv.wings.util.mapping;

import lv.wings.enums.LocaleCode;

@FunctionalInterface
public interface EntityExistenceProvider {
    boolean existsByTitleAndLocale(String newTitle, LocaleCode locale);
}
