package lv.wings.model.entity;

import java.math.BigDecimal;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import lv.wings.model.base.AuditableEntity;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "order_items")
public class OrderItem extends AuditableEntity {

	@ManyToOne
	@JoinColumn(name = "order_id", nullable = false)
	private Order order;

	@ManyToOne
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;

	@Column(nullable = false)
	private Integer amount;

	@Column(nullable = false, precision = 5, scale = 2)
	private BigDecimal priceAtOrderTime;

	@Builder
	public OrderItem(Order order, Product product, Integer amount, BigDecimal priceAtOrderTime) {
		this.order = order;
		this.product = product;
		this.amount = amount;
		this.priceAtOrderTime = priceAtOrderTime;
	}
}
