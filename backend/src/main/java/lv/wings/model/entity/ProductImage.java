package lv.wings.model.entity;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import lv.wings.model.base.TranslatableEntity;
import lv.wings.model.translation.ProductImageTranslation;

@Entity
@Table(name = "product_Images")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@Data
public class ProductImage extends TranslatableEntity<ProductImageTranslation> {

	@Column(nullable = false)
	private String src;

	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;


	public ProductImage(Product product) {
		setProduct(product);
	}

}
