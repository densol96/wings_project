package lv.wings.model.entity;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lv.wings.model.base.AuditableEntity;


@Entity
@Table(name = "customers")
@NoArgsConstructor
@Getter
@Setter
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

	@OneToOne(mappedBy = "customer")
	private Order order;

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
