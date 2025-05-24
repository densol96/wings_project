package lv.wings.service;

import java.util.List;
import java.util.Locale;
import java.util.function.Supplier;
import lv.wings.dto.interfaces.HasTranslationMethod;
import lv.wings.enums.LocaleCode;
import lv.wings.exception.entity.MissingTranslationException;
import lv.wings.model.interfaces.Localable;
import lv.wings.model.interfaces.Translatable;

public interface LocaleService {
    Locale getCurrentLocale();

    LocaleCode getCurrentLocaleCode();

    LocaleCode getDefaultLocale();

    List<LocaleCode> getAllowedLocales();

    String getCurrentLocaleString();

    <T extends Localable> T getRightTranslation(Translatable entity, Class<T> translationClass, Supplier<MissingTranslationException> exceptionSupplier);

    <T extends Localable> T getTranslationPerSelectedLocale(Translatable entity, Class<T> translationClass,
            Supplier<MissingTranslationException> exceptionSupplier, LocaleCode locale);

    String getMessage(String messageCode, Object[] args);

    String getMessage(String messageCode);

    String getMessageForSpecificLocale(String messageCode, LocaleCode localeCode);

    String getMessageForSpecificLocale(String messageCode, Object[] args, LocaleCode localeCode);

    /**
     * Validates that a translation with the default locale is present in the provided list.
     *
     * @param translations List of translation objects implementing {@link Localable}.
     * @param <T> The concrete type of translation that implements {@link Localable}.
     * @return The translation object that corresponds to the default locale.
     * @throws IllegalArgumentException if no translation with the default locale is found.
     */
    <T extends Localable> T validateDefaultLocaleIsPresent(List<T> translations);

    /**
     * Validates that there is exactly one translation per locale in the given list.
     *
     * @param translations List of translation objects implementing {@link Localable}.
     * @throws IllegalArgumentException if there are duplicate translations for the same locale.
     */
    void validateOneTranslationPerEachLocale(List<? extends Localable> translations);

    /**
     * Ensures that all required translations are present for all supported locales,
     * if the object is marked as requiring manual translation.
     *
     * @param dto The DTO containing translation data.
     * @return true if manual translation is required and all locales are present, false otherwise.
     */
    boolean validateRequiredTranslationsPresentIfManual(HasTranslationMethod entity);
}
