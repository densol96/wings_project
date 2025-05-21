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
import lv.wings.model.entity.Product;
import lv.wings.model.interfaces.HasTitle;

@Entity
@NoArgsConstructor
@Data
@Table(
        name = "product_translations",
        uniqueConstraints = @UniqueConstraint(columnNames = {"translatable_id", "locale"}))
public class ProductTranslation extends LocalableEntity<Product> implements HasTitle {

    @Column(nullable = false, unique = true, length = 50)
    private String title;

    @Column(length = 1000)
    private String description;

    @Builder
    public ProductTranslation(LocaleCode locale, Product product, String title, String description) {
        super(locale, product);
        setTitle(title);
        setDescription(description);
    }
}
