package lv.wings.dto.interfaces;

import java.util.List;
import lv.wings.enums.TranslationMethod;
import lv.wings.model.interfaces.Localable;

public interface HasTranslationMethod {
    TranslationMethod getTranslationMethod();

    <T extends Localable> List<Localable> getTranslations();
}
