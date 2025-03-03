package lv.wings.model;

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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name="Product_Picture")
@Entity
@EntityListeners(AuditingEntityListener.class)
@SQLDelete(sql = "UPDATE Product_Picture SET deleted = true WHERE product_picture_id=?")
@Where(clause = "deleted=false")
public class ProductPicture {

	@Id
	@Column(name = "product_picture_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Setter(value = AccessLevel.NONE)
	private int productPictureId;
	
	//saite uz preci
	@ManyToOne
	@JsonBackReference
	@JoinColumn(name="product_id")
	private Product product;

	
	@NotNull
	@Column(name = "reference_to_picture")
	private String referenceToPicture;

	@Column(name = "description")
	@NotNull
	@Size(min = 4, max = 150)
	private String description;
	
	@CreatedDate
	@Column(nullable = false,updatable = false)
	private LocalDateTime createDate;
	
	@LastModifiedDate
	@Column(insertable = false)
	private LocalDateTime lastModified;
	
	@CreatedBy
	//@Column(updatable = false)
	private Integer createdBy;
	
	@LastModifiedBy
	@Column(insertable = false)
	private Integer lastModifiedBy;

	//Soft delete
	@Column(name = "deleted")
	private boolean deleted = false;
	
	public ProductPicture(String referenceToPicture, String description, Product product) {
		setReferenceToPicture(referenceToPicture);
		setDescription(description);
		setProduct(product);
	}
	
}
