package lv.wings.model;

import java.time.LocalDateTime;
import java.util.Collection;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
@Table(name="product_category")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class ProductCategory {

	@Id
	@Column(name = "product_category_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Setter(value = AccessLevel.NONE)
	private int productCategoryId;
	
	@Column(name = "title")
	@NotNull
	@Size(min = 3, max = 200, message = "Kategorijas nosaukums nedrīkst saturēt mazāk par 3 vai vairāk par 200 rakstzīmēm!")
	@Pattern(regexp = "^[a-zA-ZāčēģīķļņōŗšūžĀČĒĢĪĶĻŅŌŖŠŪŽ\\s]+$", message = "Kategorijas nosaukums drīkst saturēt tikai burtus un atstarpes!")
	private String title;
	
	@Column(name = "description")
	@NotNull
	@Size(min = 4, max = 150)
	private String description;
	
	//Saite uz preci
	@OneToMany(mappedBy = "productCategory")
	@ToString.Exclude
	@JsonIgnore
	private Collection<Product> products;
	
	@CreatedDate
	@Column(nullable = false,updatable = false)
	@JsonIgnore
	private LocalDateTime createDate;
	
	@LastModifiedDate
	@Column(insertable = false)
	@JsonIgnore
	private LocalDateTime lastModified;
	
	@CreatedBy
	@Column(updatable = false)
	@JsonIgnore
	private Integer createdBy;
	
	@LastModifiedBy
	@Column(insertable = false)
	@JsonIgnore
	private Integer lastModifiedBy;
	
	public ProductCategory(String title, String description) {
		setTitle(title);
		setDescription(description);
	}
	
	
}
