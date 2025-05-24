package lv.wings.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import lv.wings.dto.interfaces.HasTranslationMethod;
import lv.wings.enums.LocaleCode;
import lv.wings.enums.TranslationMethod;
import lv.wings.exception.entity.MissingTranslationException;
import lv.wings.exception.validation.NonLocalisedException;
import lv.wings.model.interfaces.Localable;
import lv.wings.model.interfaces.Translatable;
import lv.wings.service.LocaleService;

@Service
public class LocaleServiceImpl implements LocaleService {

    private final MessageSource messageSource;

    private final static String DEFAULT_LOCALE = "lv";

    private final List<String> allowedLocales = new ArrayList<>(List.of("lv", "en"));

    public LocaleServiceImpl(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public LocaleCode getDefaultLocale() {
        return LocaleCode.from(DEFAULT_LOCALE);
    }

    @Override
    public List<LocaleCode> getAllowedLocales() {
        return allowedLocales.stream().map(l -> LocaleCode.from(l)).toList();
    }

    @Override
    public Locale getCurrentLocale() {
        return LocaleContextHolder.getLocale();
    }

    @Override
    public String getCurrentLocaleString() {
        return getCurrentLocale().getLanguage();
    }

    @Override
    public LocaleCode getCurrentLocaleCode() {
        return LocaleCode.from(getCurrentLocaleString());
    }

    @Override
    public <L extends Localable> L getRightTranslation(Translatable entity, Class<L> translationClass,
            Supplier<MissingTranslationException> exceptionSupplier) {
        return getTranslationPerSelectedLocale(entity, translationClass, exceptionSupplier, getCurrentLocaleCode());
    }

    @Override
    public <T extends Localable> T getTranslationPerSelectedLocale(Translatable entity, Class<T> translationClass,
            Supplier<MissingTranslationException> exceptionSupplier, LocaleCode locale) {
        List<Localable> translations = entity.getTranslations();
        if (translations == null)
            throw exceptionSupplier.get();
        return translations
                .stream()
                .filter(translation -> translation.getLocale() == locale)
                .findFirst()
                .or(() -> translations
                        .stream()
                        .filter(t -> t.getLocale().getCode().equalsIgnoreCase(DEFAULT_LOCALE))
                        .findFirst())
                .map(translation -> {
                    if (translationClass.isInstance(translation)) {
                        return translationClass.cast(translation);
                    } else {
                        throw exceptionSupplier.get();
                    }
                })
                .orElseThrow(exceptionSupplier);
    }

    @Override
    public String getMessage(String messageCode) {
        return getMessage(messageCode, null);
    }

    @Override
    public String getMessage(String messageCode, Object[] args) {
        return messageSource.getMessage(messageCode, args, getCurrentLocale());
    }

    @Override
    public String getMessageForSpecificLocale(String messageCode, LocaleCode localeCode) {
        return getMessageForSpecificLocale(messageCode, null, localeCode);
    }

    @Override
    public String getMessageForSpecificLocale(String messageCode, Object[] args, LocaleCode localeCode) {
        return messageSource.getMessage(messageCode, args, new Locale(localeCode.getCode()));
    }

    @Override
    public <T extends Localable> T validateDefaultLocaleIsPresent(List<T> translations) {
        return translations
                .stream()
                .filter(tr -> tr.getLocale() == getDefaultLocale())
                .findFirst()
                .orElseThrow(() -> new NonLocalisedException("Noklusētā valoda (" + DEFAULT_LOCALE + ") vienmēr ir obligāta, veidojot jaunu ierakstu.",
                        HttpStatus.UNPROCESSABLE_ENTITY));
    }

    @Override
    public void validateOneTranslationPerEachLocale(List<? extends Localable> translations) {
        if (translations.size() != translations.stream().map(l -> l.getLocale()).collect(Collectors.toSet()).size())
            throw new NonLocalisedException("Valodu sarakstā ir dublikāti — katrai valodai jābūt tikai vienam tulkojumam.", HttpStatus.UNPROCESSABLE_ENTITY);
    }


    @Override
    public boolean validateRequiredTranslationsPresentIfManual(HasTranslationMethod entity) {
        TranslationMethod translationMethod = entity.getTranslationMethod();

        boolean isManualTranslation = translationMethod == TranslationMethod.MANUAL;

        if (isManualTranslation && (entity.getTranslations().size() != getAllowedLocales().size()))
            throw new NonLocalisedException("Manuālajā režīmā jānorāda tulkojumi visās atbalstītajās valodās.", HttpStatus.UNPROCESSABLE_ENTITY);

        return isManualTranslation;
    }
}
