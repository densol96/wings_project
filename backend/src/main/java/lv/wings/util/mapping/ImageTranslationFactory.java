package lv.wings.util.mapping;

import lv.wings.enums.LocaleCode;

@FunctionalInterface
public interface ImageTranslationFactory<I, IT> {
    IT create(LocaleCode locale, I image, String title);
}
