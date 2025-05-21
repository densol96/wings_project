package lv.wings.model.entity;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lv.wings.enums.DeliveryMethod;
import lv.wings.model.base.TranslatableEntity;
import lv.wings.model.translation.DeliveryTypeTranslation;

@Entity
@Table(name = "delivery_types")
@Getter
@Setter
@NoArgsConstructor
public class DeliveryType extends TranslatableEntity<DeliveryTypeTranslation> {

	@OneToMany(mappedBy = "deliveryType", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<DeliveryPrice> prices = new ArrayList<>();

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private DeliveryMethod method;

	public DeliveryType(DeliveryMethod method) {
		this.method = method;
	}
}
