package lv.wings.service.impl;

import java.util.Locale;
import java.util.function.Supplier;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import lv.wings.exception.entity.MissingTranslationException;
import lv.wings.model.interfaces.Localable;
import lv.wings.model.interfaces.Translatable;
import lv.wings.service.LocaleService;

@Service
public class LocaleServiceImpl implements LocaleService {

    private final String DEFAULT_LOCALE = "lv";

    @Override
    public Locale getCurrentLocale() {
        return LocaleContextHolder.getLocale();
    }

    @Override
    public String getCurrentLocaleCode() {
        return getCurrentLocale().getLanguage();
    }

    @Override
    public <T extends Localable> T getRightTranslation(Translatable entity, Class<T> translationClass, Supplier<MissingTranslationException> exceptionSupplier) {
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
}
