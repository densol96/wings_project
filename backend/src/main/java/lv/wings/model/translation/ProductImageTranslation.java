package lv.wings.model.translation;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import lv.wings.enums.LocaleCode;
import lv.wings.model.base.ImageLocalableEntity;
import lv.wings.model.entity.ProductImage;

@Entity
@Table(name = "product_picture_translations")
@NoArgsConstructor
@Data
public class ProductImageTranslation extends ImageLocalableEntity<ProductImage> {

    @Builder
    public ProductImageTranslation(LocaleCode locale, ProductImage picture, String alt) {
        super(locale, picture, alt);
    }
}
