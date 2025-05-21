package lv.wings.model.translation;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import lv.wings.enums.LocaleCode;
import lv.wings.model.base.LocalableEntity;
import lv.wings.model.entity.ProductCategory;

@Entity
@NoArgsConstructor
@Data
@Table(
        name = "product_category_translations",
        uniqueConstraints = @UniqueConstraint(columnNames = {"translatable_id", "locale"}))
public class ProductCategoryTranslation extends LocalableEntity<ProductCategory> {

    @Column(unique = true, nullable = false, length = 50)
    private String title;

    @Column(length = 300)
    private String description;

    @Builder
    public ProductCategoryTranslation(LocaleCode locale, ProductCategory category, String title, String description) {
        super(locale, category);
        setTitle(title);
        setDescription(description);
    }
}
