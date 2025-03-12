package lv.wings.model.translation;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lv.wings.model.base.LocalableEntity;
import lv.wings.model.entity.PaymentType;

@Entity
@Table(name = "payment_type_translations")
@NoArgsConstructor
@Data
public class PaymentTypeTranslation extends LocalableEntity<PaymentType> {
    @Column(unique = true, nullable = false)
    private String title;

    @Column(name = "description")
    private String description;
}
