package lv.wings.model.entity;

import java.time.LocalDateTime;
import java.util.Collection;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
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
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name = "delivery_type")
@Entity
@EntityListeners(AuditingEntityListener.class)
@SQLDelete(sql = "UPDATE delivery_type SET deleted = true WHERE delivery_type_id=?")
@Where(clause = "deleted=false")
public class DeliveryType {

	@Id
	@Column(name = "delivery_type_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Setter(value = AccessLevel.NONE)
	private int deliveryTypeId;

	// TODO iespejams papildus anotacijas
	@Column(name = "title")
	private String title;

	@Column(name = "description")
	private String description;

	// TODO
	@OneToMany(mappedBy = "deliveryType")
	@ToString.Exclude
	@JsonIgnore
	private Collection<Purchase> purchases;

	@CreatedDate
	@Column(nullable = false, updatable = false)
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

	// Soft delete
	@Column(name = "deleted")
	private boolean deleted = false;

	public DeliveryType(String title, String description) {
		setTitle(title);
		setDescription(description);
	}
}
