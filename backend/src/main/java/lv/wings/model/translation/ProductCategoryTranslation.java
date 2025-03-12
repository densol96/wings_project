package lv.wings.model.translation;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lv.wings.enums.LocaleCode;
import lv.wings.model.base.LocalableEntity;
import lv.wings.model.entity.EventCategory;
import lv.wings.model.entity.ProductCategory;

@Entity
@Table(name = "product_category_translations")
@NoArgsConstructor
@Data
public class ProductCategoryTranslation extends LocalableEntity<ProductCategory> {

    @Column(unique = true, nullable = false)
    private String title;

    private String description;

    @Builder
    public ProductCategoryTranslation(LocaleCode locale, ProductCategory category, String title, String description) {
        super(locale, category);
        setTitle(title);
        setDescription(description);
    }
}
