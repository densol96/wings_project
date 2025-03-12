package lv.wings.model.interfaces;

import java.util.List;

public interface Translatable extends Auditable {
    List<Localable> getTranslations();
}
