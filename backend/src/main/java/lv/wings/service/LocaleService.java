package lv.wings.service;

import java.util.Locale;

import lv.wings.model.interfaces.Localable;
import lv.wings.model.interfaces.Translatable;

public interface LocaleService {
    Locale getCurrentLocale();

    String getCurrentLocaleCode();

    Localable getRightTranslation(Translatable entity);
}
