package lv.wings.model.entity;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.event.spi.EventManager.CacheActionDescription;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lv.wings.enums.Country;
import lv.wings.model.base.AuditableEntity;


@Entity
@Table(name = "customers")
@NoArgsConstructor
@Getter
@Setter
@SQLDelete(sql = "UPDATE Customer SET deleted = true WHERE customer_id=?")
@Where(clause = "deleted=false")
public class Customer extends AuditableEntity {

	@Column(nullable = false, length = 50)
	private String firstName;

	@Column(nullable = false, length = 50)
	private String lastName;

	@Column(nullable = false, length = 100)
	private String email;

	@Column(nullable = false, length = 12)
	private String phoneNumber;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "address_id")
	private Address address;

	@OneToOne(mappedBy = "customer")
	private Order order;

	@Column(nullable = false)
	private boolean deleted = false;

	@Builder
	public Customer(String firstName, String lastName, String email, String phoneNumber, Address address, Order order) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.order = order;
	}
}
