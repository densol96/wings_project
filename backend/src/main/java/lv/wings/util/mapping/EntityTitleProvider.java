package lv.wings.util.mapping;

import lv.wings.enums.LocaleCode;

@FunctionalInterface
public interface EntityTitleProvider<T> {
    String getTitle(T entity, LocaleCode locale);
}
