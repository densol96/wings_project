package lv.wings.model.entity;

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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name = "Purchase_Elements")
@Entity
@EntityListeners(AuditingEntityListener.class)
@SQLDelete(sql = "UPDATE Purchase_Elements SET deleted = true WHERE purchase_element_id=?")
@Where(clause = "deleted=false")
public class PurchaseElement {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Setter(value = AccessLevel.NONE)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "purchase_id", nullable = false)
	private Purchase purchase;

	@ManyToOne
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;

	@Column(nullable = false)
	private Integer amount;

	@CreatedDate
	@Column(nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@LastModifiedDate
	@Column(insertable = false)
	private LocalDateTime lastModifiedByAdmin;

	@LastModifiedBy
	@Column(insertable = false)
	private Integer lastModifiedBy;

	// Soft delete
	@Column(name = "deleted")
	private boolean deleted = false;

	@Builder
	public PurchaseElement(Purchase purchase, Product product, Integer amount) {
		setPurchase(purchase);
		setProduct(product);
		setAmount(amount);
	}
}
