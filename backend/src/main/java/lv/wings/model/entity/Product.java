package lv.wings.model.entity;


import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lv.wings.model.base.TranslatableEntity;
import lv.wings.model.translation.ProductTranslation;


@Entity
@Table(name = "products")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@Data
public class Product extends TranslatableEntity<ProductTranslation> {

	@Column(nullable = false)
	private Float price;

	@Column(nullable = false)
	private Integer amount;

	@ManyToOne
	@JoinColumn(name = "product_category_id")
	private ProductCategory category;

	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ProductPicture> images = new ArrayList<>();

	@OneToMany(mappedBy = "product")
	private List<PurchaseElement> purchaseElement = new ArrayList<>();

	@Builder
	public Product(Float price, Integer amount, ProductCategory category) {
		setPrice(price);
		setAmount(amount);
		setCategory(category);
	}

}
