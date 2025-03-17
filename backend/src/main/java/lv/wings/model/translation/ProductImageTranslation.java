package lv.wings.model.translation;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import lv.wings.enums.LocaleCode;
import lv.wings.model.base.LocalableEntity;
import lv.wings.model.entity.ProductImage;
import lv.wings.model.interfaces.ImageTranslation;

@Entity
@Table(name = "product_picture_translations")
@NoArgsConstructor
@Data
public class ProductImageTranslation extends LocalableEntity<ProductImage> implements ImageTranslation {
    @Column(nullable = false)
    private String alt;

    @Builder
    public ProductImageTranslation(LocaleCode locale, ProductImage picture, String alt) {
        super(locale, picture);
        setAlt(alt);
    }
}
