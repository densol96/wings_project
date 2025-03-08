package lv.wings.service;

import java.util.Locale;

import lv.wings.model.translation.Localable;
import lv.wings.model.translation.Translatable;

public interface LocaleService {
    Locale getCurrentLocale();

    String getCurrentLocaleCode();

    Localable getRightTranslation(Translatable entity);
}
