package lv.wings.service.impl;

import java.util.List;
import java.util.Locale;
import java.util.function.Supplier;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import lv.wings.enums.LocaleCode;
import lv.wings.exception.entity.MissingTranslationException;
import lv.wings.model.interfaces.Localable;
import lv.wings.model.interfaces.Translatable;
import lv.wings.repo.security.IMyAuthorityRepo;
import lv.wings.service.LocaleService;

@Service
public class LocaleServiceImpl implements LocaleService {

    private final IMyAuthorityRepo IMyAuthorityRepo;
    private final MessageSource messageSource;

    private final static String DEFAULT_LOCALE = "lv";


    public LocaleServiceImpl(IMyAuthorityRepo IMyAuthorityRepo, MessageSource messageSource) {
        this.IMyAuthorityRepo = IMyAuthorityRepo;
        this.messageSource = messageSource;
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
        List<Localable> translations = entity.getTranslations();
        if (translations == null)
            throw exceptionSupplier.get();
        return translations
                .stream()
                .filter(translation -> translation.getLocale() == getCurrentLocaleCode()) // fine for enums since ther are singletones
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
}
