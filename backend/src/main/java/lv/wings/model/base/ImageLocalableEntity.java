package lv.wings.model.base;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.NoArgsConstructor;
import lv.wings.enums.LocaleCode;
import lv.wings.model.interfaces.ImageTranslation;
import lv.wings.model.interfaces.Imageable;
import lv.wings.model.interfaces.Translatable;

@MappedSuperclass
@NoArgsConstructor
@Data
public abstract class ImageLocalableEntity<I extends Imageable & Translatable> extends LocalableEntity<I> implements ImageTranslation {

    @Column(nullable = false)
    private String alt;

    protected ImageLocalableEntity(LocaleCode locale, I picture, String alt) {
        super(locale, picture);
        this.alt = alt;
    }
}
