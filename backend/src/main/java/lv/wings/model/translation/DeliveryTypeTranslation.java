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
import lv.wings.model.entity.DeliveryType;

@Entity
@NoArgsConstructor
@Data
@Table(
        name = "delivery_type_translations",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"translatable_id", "locale"}),
                @UniqueConstraint(columnNames = {"title", "locale"})
        })
public class DeliveryTypeTranslation extends LocalableEntity<DeliveryType> {

    @Column(nullable = false, length = 50)
    private String title;

    @Column(nullable = false, length = 100)
    private String description;

    @Builder
    public DeliveryTypeTranslation(String title, String description, LocaleCode locale, DeliveryType deliveryType) {
        super(locale, deliveryType);
        this.title = title;
        this.description = description;

    }
}
