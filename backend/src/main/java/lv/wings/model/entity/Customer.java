package lv.wings.model.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import lv.wings.model.base.AuditableEntity;


@Entity
@Table(name = "customers")
@NoArgsConstructor
@Data
@EntityListeners(AuditingEntityListener.class)
@SQLDelete(sql = "UPDATE Customer SET deleted = true WHERE customer_id=?")
@Where(clause = "deleted=false")
public class Customer extends AuditableEntity {

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String surname;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	private String adress;

	// Soft delete
	@Column(name = "deleted")
	private boolean deleted = false;

	@Builder
	public Customer(String name, String surname, String email, String adress) {
		setName(name);
		setSurname(surname);
		setEmail(email);
		setAdress(adress);
	}

}
