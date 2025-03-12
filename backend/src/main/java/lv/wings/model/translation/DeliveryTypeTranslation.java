package lv.wings.model.translation;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lv.wings.model.base.LocalableEntity;
import lv.wings.model.entity.DeliveryType;

@Entity
@Table(name = "delivery_type_translations")
@NoArgsConstructor
@Data
public class DeliveryTypeTranslation extends LocalableEntity<DeliveryType> {

    @Column(unique = true, nullable = false)
    private String title;

    private String description;
}
