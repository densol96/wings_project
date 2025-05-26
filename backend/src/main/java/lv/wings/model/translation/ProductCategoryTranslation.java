package lv.wings.model.translation;

import org.hibernate.annotations.SQLDelete;
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
import lv.wings.model.interfaces.LocalableWithTitle;

@Entity
@NoArgsConstructor
@Data
@Table(
        name = "product_category_translations",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"translatable_id", "locale"}),
        // @UniqueConstraint(columnNames = {"title", "locale"}) // Unique should be checked in service since this entity can be softly deleted
        })
@SQLDelete(sql = "UPDATE product_category_translations SET deleted = true WHERE id=?")
public class ProductCategoryTranslation extends LocalableEntity<ProductCategory> implements LocalableWithTitle {


    @Column(nullable = false, length = 50)
    private String title;

    @Column(length = 300)
    private String description;

    @Column(nullable = false, columnDefinition = "TINYINT(1)")
    private boolean deleted = false;

    @Builder
    public ProductCategoryTranslation(LocaleCode locale, ProductCategory category, String title, String description) {
        super(locale, category);
        setTitle(title);
        setDescription(description);
    }
}
