package lv.wings.model.entity;


import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.List;


import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
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
@EntityListeners(AuditingEntityListener.class)
public class ProductCategory extends TranslatableEntity<ProductCategoryTranslation> {

	@OneToMany(mappedBy = "category")
	private List<Product> products = new ArrayList<>();

}
