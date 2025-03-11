package lv.wings.service;

import java.util.Locale;
import java.util.function.Supplier;
import lv.wings.exception.entity.MissingTranslationException;
import lv.wings.model.interfaces.Localable;
import lv.wings.model.interfaces.Translatable;

public interface LocaleService {
    Locale getCurrentLocale();

    String getCurrentLocaleCode();

    <T extends Localable> T getRightTranslation(Translatable entity, Class<T> translationClass, Supplier<MissingTranslationException> exceptionSupplier);
}
