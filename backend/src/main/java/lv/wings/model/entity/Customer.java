package lv.wings.model.entity;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import lombok.Builder;
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

	@Column(nullable = false, length = 50)
	private String firstName;

	@Column(nullable = false, length = 50)
	private String lastName;

	@Column(nullable = false, length = 100)
	private String email;

	@Column(nullable = false, length = 100)
	private String street;

	@Column(nullable = false, length = 10)
	private String houseNumber;

	@Column(length = 10)
	private String apartment;

	@Column(nullable = false, length = 50)
	private String city;

	@Column(nullable = false, length = 10)
	private String postalCode;

	@Column(nullable = false, length = 50)
	private String country;

	@Column(length = 500)
	private String additionalDetails;

	@OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
	private Order order;

	@Column(nullable = false)
	private boolean deleted = false;

	@Builder
	public Customer(String firstName, String lastName, String email,
			String street, String houseNumber, String apartment,
			String city, String postalCode, String country, Order order, String additionalDetails) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.street = street;
		this.houseNumber = houseNumber;
		this.apartment = apartment;
		this.city = city;
		this.postalCode = postalCode;
		this.country = country;
		this.additionalDetails = additionalDetails;
	}
}
