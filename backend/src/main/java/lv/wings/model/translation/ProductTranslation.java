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
import lv.wings.model.entity.Product;
import lv.wings.model.interfaces.HasTitle;

@Entity
@NoArgsConstructor
@Data
@Table(
        name = "product_translations",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"translatable_id", "locale"})
        })
@SQLDelete(sql = "UPDATE product_translations SET deleted = true WHERE id=?")
public class ProductTranslation extends LocalableEntity<Product> implements HasTitle {

    // Unique should ne checked in service since this entity can be softly deleted
    @Column(nullable = false, length = 50)
    private String title;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false, columnDefinition = "TINYINT(1)")
    private boolean deleted = false;

    @Builder
    public ProductTranslation(LocaleCode locale, Product product, String title, String description) {
        super(locale, product);
        setTitle(title);
        setDescription(description);
    }
}
