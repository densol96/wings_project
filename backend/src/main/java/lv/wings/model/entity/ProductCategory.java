package lv.wings.model.entity;

import java.util.ArrayList;
import java.util.List;


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
public class ProductCategory extends TranslatableEntity<ProductCategoryTranslation> {

	@OneToMany(mappedBy = "category")
	private List<Product> products = new ArrayList<>();

}
