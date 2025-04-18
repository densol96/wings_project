package lv.wings.service;

import java.util.Locale;
import java.util.function.Supplier;
import lv.wings.enums.LocaleCode;
import lv.wings.exception.entity.MissingTranslationException;
import lv.wings.model.interfaces.Localable;
import lv.wings.model.interfaces.Translatable;

public interface LocaleService {
    Locale getCurrentLocale();

    LocaleCode getCurrentLocaleCode();

    String getCurrentLocaleString();

    <T extends Localable> T getRightTranslation(Translatable entity, Class<T> translationClass, Supplier<MissingTranslationException> exceptionSupplier);

    <T extends Localable> T getTranslationPerSelectedLocale(Translatable entity, Class<T> translationClass,
            Supplier<MissingTranslationException> exceptionSupplier, LocaleCode locale);

    String getMessage(String messageCode, Object[] args);

    String getMessage(String messageCode);

    String getMessageForSpecificLocale(String messageCode, LocaleCode localeCode);

    String getMessageForSpecificLocale(String messageCode, Object[] args, LocaleCode localeCode);
}
