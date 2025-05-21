package lv.wings.model.translation;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import lv.wings.enums.LocaleCode;
import lv.wings.model.base.ImageLocalableEntity;
import lv.wings.model.entity.ProductImage;

@Entity
@NoArgsConstructor
@Data
@Table(
        name = "product_picture_translations",
        uniqueConstraints = @UniqueConstraint(columnNames = {"translatable_id", "locale"}))
public class ProductImageTranslation extends ImageLocalableEntity<ProductImage> {

    @Builder
    public ProductImageTranslation(LocaleCode locale, ProductImage picture, String alt) {
        super(locale, picture, alt);
    }
}
