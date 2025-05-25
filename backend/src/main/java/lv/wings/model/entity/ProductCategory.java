package lv.wings.model.entity;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.annotations.SQLDelete;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lv.wings.model.base.TranslatableEntity;
import lv.wings.model.translation.ProductCategoryTranslation;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name = "product_categories")
@Entity
@SQLDelete(sql = "UPDATE product_categories SET deleted = true WHERE id=?")
public class ProductCategory extends TranslatableEntity<ProductCategoryTranslation> {

	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
	private List<Product> products = new ArrayList<>();

	@Column(nullable = false, columnDefinition = "TINYINT(1)")
	private boolean deleted = false;
}
