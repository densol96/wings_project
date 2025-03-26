package lv.wings.model.entity;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lv.wings.model.base.OwnerableEntity;
import lv.wings.model.base.TranslatableEntity;
import lv.wings.model.translation.EventTranslation;
import lv.wings.model.translation.ProductTranslation;


@Entity
@Table(name = "products")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@Data
public class Product extends OwnerableEntity<ProductTranslation, ProductImage> {

	@Column(nullable = false)
	private Double price;

	@Column(nullable = false)
	private Integer amount;

	@ManyToOne
	@JoinColumn(name = "product_category_id")
	private ProductCategory category;

	@OneToMany(mappedBy = "product")
	private List<PurchaseElement> purchaseElement = new ArrayList<>();

	@OneToMany(mappedBy = "product")
	private List<ProductMaterial> madeOfMaterials = new ArrayList<>();

	@ManyToMany
	@JoinTable(
			name = "product_color",
			joinColumns = @JoinColumn(name = "product_id"),
			inverseJoinColumns = @JoinColumn(name = "color_id"))
	private List<Color> colors = new ArrayList<>();

	@Builder
	public Product(Double price, Integer amount, ProductCategory category) {
		setPrice(price);
		setAmount(amount);
		setCategory(category);
	}

}
