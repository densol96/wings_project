package lv.wings.model.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lv.wings.model.base.TranslatableEntity;
import lv.wings.model.translation.ProductCategoryTranslation;
import lv.wings.model.translation.ProductPictureTranslation;

@Entity
@Table(name = "product_pictures")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@Data
public class ProductPicture extends TranslatableEntity<ProductPictureTranslation> {

	@Column(nullable = false)
	private String src;

	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;


	public ProductPicture(Product product) {
		setProduct(product);
	}

}
