package lv.wings.model.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lv.wings.model.base.AuditableEntity;

@NoArgsConstructor
@Entity
@Data
@Table(name = "purchases")
@SQLDelete(sql = "UPDATE Purchase SET deleted = true WHERE purchase_id=?")
@Where(clause = "deleted=false")
public class Purchase extends AuditableEntity {

	private String deliveryDetails;

	// @ManyToOne
	// @JoinColumn(name = "delivery_type_id", nullable = false)
	// private DeliveryType deliveryType;

	@ManyToOne
	@JoinColumn(name = "payment_type_id", nullable = false)
	private PaymentType paymentType;

	@ManyToOne
	@JoinColumn(name = "customer_id", nullable = false)
	private Customer customer;

	@OneToMany(mappedBy = "purchase")
	private List<PurchaseElement> purchaseElement = new ArrayList<>();

	@Column(name = "deleted")
	private boolean deleted = false;

	@Builder
	public Purchase(
			DeliveryType deliveryType,
			PaymentType paymentType,
			Customer customer,
			LocalDateTime deliveryDate,
			String deliveryDetails) {
		setDeliveryType(deliveryType);
		setPaymentType(paymentType);
		setCustomer(customer);
		setDeliveryDetails(deliveryDetails);
	}

}
