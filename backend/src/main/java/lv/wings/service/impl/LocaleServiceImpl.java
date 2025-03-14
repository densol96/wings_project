package lv.wings.service.impl;

import java.util.Locale;
import java.util.function.Supplier;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

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
    public String getCurrentLocaleCode() {
        return getCurrentLocale().getLanguage();
    }

    @Override
    public <L extends Localable> L getRightTranslation(Translatable entity, Class<L> translationClass, Supplier<MissingTranslationException> exceptionSupplier) {
        return entity.getTranslations()
                .stream()
                .filter(translation -> translation.getLocale().getCode().equalsIgnoreCase(getCurrentLocaleCode()))
                .findFirst()
                .or(() -> entity.getTranslations()
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


    public String getMessage(String messageCode) {
        return getMessage(messageCode, null);
    }

    public String getMessage(String messageCode, Object[] args) {
        return messageSource.getMessage(messageCode, args, getCurrentLocale());
    }

}
