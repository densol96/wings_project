package lv.wings.model.entity;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;
import lv.wings.enums.DeliveryMethod;
import lv.wings.model.base.TranslatableEntity;
import lv.wings.model.translation.DeliveryTypeTranslation;

@Entity
@Table(name = "delivery_types")
@Data
@NoArgsConstructor
@SQLDelete(sql = "UPDATE delivery_type SET deleted = true WHERE delivery_type_id=?")
@Where(clause = "deleted=false")
public class DeliveryType extends TranslatableEntity<DeliveryTypeTranslation> {

	// @OneToMany(mappedBy = "deliveryType")
	// private List<Purchase> purchases = new ArrayList<>();

	@OneToMany(mappedBy = "deliveryType", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<DeliveryPrice> prices = new ArrayList<>();

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private DeliveryMethod method;

	// Soft delete
	@Column(name = "deleted")
	private boolean deleted = false;

	public DeliveryType(DeliveryMethod method) {
		this.method = method;
	}
}
