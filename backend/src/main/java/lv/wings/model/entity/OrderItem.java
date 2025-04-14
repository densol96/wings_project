package lv.wings.model.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;
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
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lv.wings.model.base.AuditableEntity;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "order_items")
@SQLDelete(sql = "UPDATE order_items SET deleted = true WHERE id = ?")
@Where(clause = "deleted=false")
public class OrderItem extends AuditableEntity {

	@ManyToOne
	@JoinColumn(name = "order_id", nullable = false)
	private Order order;

	@ManyToOne
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;

	@Column(nullable = false)
	private Integer amount;

	@Column(nullable = false)
	private BigDecimal priceAtOrderTime;

	// Soft delete
	@Column(name = "deleted")
	private boolean deleted = false;

	@Builder
	public OrderItem(Order purchase, Product product, Integer amount) {

	}
}
