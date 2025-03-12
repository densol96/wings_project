package lv.wings.model.translation;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import lv.wings.enums.LocaleCode;
import lv.wings.model.base.LocalableEntity;
import lv.wings.model.entity.ProductPicture;

@Entity
@Table(name = "product_picture_translations")
@NoArgsConstructor
@Data
public class ProductPictureTranslation extends LocalableEntity<ProductPicture> {
    @Column(nullable = false)
    private String alt;

    @Builder
    public ProductPictureTranslation(LocaleCode locale, ProductPicture picture, String alt) {
        super(locale, picture);
        setAlt(alt);
    }
}
