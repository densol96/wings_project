package lv.wings.model;

import java.time.LocalDateTime;
import java.util.Collection;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lv.wings.poi.PoiMeta;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name="Product")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Product {
	
	@Id
	@Column(name = "product_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Setter(value = AccessLevel.NONE)
	private int productId;
	
	//saite no kategorijas
	@ManyToOne
	@JsonManagedReference
	@JoinColumn(name="product_category_id")
	private ProductCategory productCategory;
	
	//saite uz bildi
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JsonManagedReference
	@ToString.Exclude
	private Collection<ProductPicture> productPictures;

	
	//saite uz pirkuma_elementu
	@OneToMany(mappedBy = "product")
	@ToString.Exclude
	private Collection<PurchaseElement> purchaseElement;

	
	@Column(name = "title")
	@NotNull
	@Size(min = 4, max = 50)
	@Pattern(regexp = "[A-ZĒŪĪĻĶĢŠĀŽČŅa-zēūīļķģšāžčņ ]+", message = "Only letters and space are allowed")
	private String title;
	
	@Column(name = "description")
	@NotNull
	@Size(min = 4, max = 1000)
	private String description;
	
	@Column(name = "price")
	@NotNull
	@Min(0)
	@PoiMeta(name="Cena (EUR)", valueFormat="{} EUR")
	private float price;
	
	@Column(name = "amount")
	@NotNull
	@Min(0)
	private int amount;
	
	@CreatedDate
	@Column(nullable = false,updatable = false)
	private LocalDateTime createDate;
	
	@LastModifiedDate
	@Column(insertable = false)
	private LocalDateTime lastModified;
	
	@CreatedBy
	@Column(updatable = false)
	private Integer createdBy;
	
	@LastModifiedBy
	@Column(insertable = false)
	private Integer lastModifiedBy;
	
	public Product(String title, String description, float price, int amount, ProductCategory productCategory) {
		setTitle(title);
		setDescription(description);
		setPrice(price);
		setAmount(amount);
		setProductCategory(productCategory);
	}
	

}
