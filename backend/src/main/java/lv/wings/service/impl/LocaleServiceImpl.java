package lv.wings.service.impl;

import java.util.Locale;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import lv.wings.model.translation.Localable;
import lv.wings.model.translation.Translatable;
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
    public Localable getRightTranslation(Translatable entity) {
        return entity.getTranslations()
                .stream()
                .filter(t -> t.getLocale().getCode().equalsIgnoreCase(getCurrentLocaleCode()))
                .findFirst()
                .orElseGet(() -> entity.getTranslations()
                        .stream()
                        .filter(t -> t.getLocale().getCode().equalsIgnoreCase(DEFAULT_LOCALE))
                        .findFirst()
                        .orElse(null));
    }
}
