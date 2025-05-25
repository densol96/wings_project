package lv.wings.dto.interfaces;

import java.util.List;
import lv.wings.enums.TranslationMethod;
import lv.wings.model.interfaces.LocalableWithTitle;

public interface HasTranslationMethod {
    TranslationMethod getTranslationMethod();

    <T extends LocalableWithTitle> List<LocalableWithTitle> getTranslations();
}
