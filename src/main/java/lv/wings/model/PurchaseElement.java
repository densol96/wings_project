package lv.wings.model;

import java.time.LocalDateTime;

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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name="Purchase_Elements")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class PurchaseElement {

	@Id
	@Column(name = "purchase_element_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Setter(value = AccessLevel.NONE)
	private int purchaseElementId;
	
	//TODO te nak pirkums ID MARKUSS
	@ManyToOne
	@JoinColumn(name = "purchase_id")
	private Purchase purchase;
	
	//saite uz preces
	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;
	
	@Column(name = "amount")
	@Min(1)
	private int amount;
	
	@CreatedDate
	@Column(nullable = false,updatable = false)
	private LocalDateTime createDate;
	
	@LastModifiedDate
	@Column(insertable = false)
	private LocalDateTime lastModifiedByAdmin;
	
	@LastModifiedBy
	@Column(insertable = false)
	private Integer lastModifiedBy;
	
	public PurchaseElement(Purchase purchase, Product product, int amount) {
		setPurchase(purchase);
		setProduct(product);
		setAmount(amount);
	}
}
