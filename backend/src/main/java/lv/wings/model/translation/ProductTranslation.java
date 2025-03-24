package lv.wings.model.translation;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import lv.wings.enums.LocaleCode;
import lv.wings.model.base.LocalableEntity;
import lv.wings.model.entity.Product;

@Entity
@Table(name = "product_translations")
@NoArgsConstructor
@Data
public class ProductTranslation extends LocalableEntity<Product> {

    @Column(nullable = false, unique = true)
    private String title;

    private String description;

    @Builder
    public ProductTranslation(LocaleCode locale, Product product, String title, String description) {
        super(locale, product);
        setTitle(title);
        setDescription(description);
    }

}
