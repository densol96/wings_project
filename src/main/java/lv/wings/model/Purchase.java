package lv.wings.model;

import java.time.LocalDateTime;
import java.util.Collection;

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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name = "Purchase")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Purchase {
	
	@Id
	@Column(name = "purchase_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Setter(value = AccessLevel.NONE)
	private int purchaseId;
	

	@ManyToOne
	@JoinColumn(name = "delivery_type_id")
	private DeliveryType deliveryType;
	
	
	@ManyToOne
	@JoinColumn(name = "payment_type_id")
	private PaymentType paymentType;
	
	
	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;
	
	
	@Column(name = "delivery_date")
	private LocalDateTime deliveryDate;
	
	
	@Column(name = "delivery_details")
	private String deliveryDetails;
	

	@OneToMany(mappedBy = "purchase")
	@ToString.Exclude
	private Collection<PurchaseElement> purchaseElement;
	
	@LastModifiedDate
	@Column(insertable = false)
	private LocalDateTime lastModifiedByAdmin;
	
	@LastModifiedBy
	@Column(insertable = false)
	private Integer lastModifiedBy;

	
	public Purchase(DeliveryType deliveryType, PaymentType paymentType, Customer customer, LocalDateTime deliveryDate, String deliveryDetails) {
		setDeliveryType(deliveryType);
		setPaymentType(paymentType);
		setCustomer(customer);
		setDeliveryDate(deliveryDate);
		setDeliveryDetails(deliveryDetails);
	}

}
