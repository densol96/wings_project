package lv.wings.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import lv.wings.model.base.ImageableEntity;
import lv.wings.model.translation.ProductImageTranslation;

@Entity
@Table(name = "product_images")
@NoArgsConstructor
@Getter
@Setter
public class ProductImage extends ImageableEntity<ProductImageTranslation, Product> {

	@Builder
	public ProductImage(Product product, String src, Integer position) {
		super(product, src, position);
	}

}
