package lv.wings.model.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import com.stripe.param.billingportal.SessionCreateParams.Locale;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lv.wings.enums.LocaleCode;
import lv.wings.enums.OrderStatus;
import lv.wings.model.base.AuditableEntity;

@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "orders")
@SQLDelete(sql = "UPDATE orders SET deleted = true WHERE id = ?")
@Where(clause = "deleted=false")
public class Order extends AuditableEntity {

	@Column(nullable = false, unique = true)
	private String paymentIntentId;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private OrderStatus status = OrderStatus.IN_PROGRESS;

	@ManyToOne
	@JoinColumn(name = "delivery_price_id", nullable = false)
	private DeliveryPrice deliveryVariation;

	@Column(nullable = false)
	private BigDecimal deliveryPriceAtOrderTime;

	@ManyToOne
	@JoinColumn(name = "terminal_id")
	private Terminal terminal; // can be null unless DeliveryType for Terminals

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "customer_id", nullable = false)
	private Customer customer;

	@ManyToOne
	@JoinColumn(name = "coupon_id")
	private Coupon appliedCoupon;

	@Column(nullable = false)
	private BigDecimal discountAtOrderTime = BigDecimal.ZERO;

	@Column(nullable = false)
	private BigDecimal total;

	@Column(length = 500)
	private String additionalDetails;

	@Enumerated(EnumType.STRING)
	private LocaleCode locale = LocaleCode.LV;

	@Column(name = "deleted", nullable = false)
	private boolean deleted = false;

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<OrderItem> orderItems = new ArrayList<>();

	@Builder
	public Order(
			String paymentIntentId,
			DeliveryPrice deliveryVariation,
			Customer customer,
			Terminal terminal,
			BigDecimal total,
			String additionalDetails,
			Coupon appliedCoupon,
			BigDecimal discountAtOrderTime,
			LocaleCode locale) {
		this.paymentIntentId = paymentIntentId;
		this.deliveryVariation = deliveryVariation;
		this.deliveryPriceAtOrderTime = deliveryVariation.getPrice();
		this.customer = customer;
		this.terminal = terminal;
		this.total = total;
		this.appliedCoupon = appliedCoupon;
		this.discountAtOrderTime = discountAtOrderTime;
		this.additionalDetails = additionalDetails;
		this.locale = locale;
	}
}
