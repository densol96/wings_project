package lv.wings.model.entity;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lv.wings.enums.Country;
import lv.wings.model.base.AuditableEntity;

@Entity
@Table(name = "addresses")
@NoArgsConstructor
@Getter
@Setter
@SQLDelete(sql = "UPDATE addresses SET deleted = true WHERE customer_id=?")
@Where(clause = "deleted=false")
public class Address extends AuditableEntity {
    @Column(nullable = false, length = 100)
    private String street;

    @Column(nullable = false, length = 10)
    private String houseNumber;

    @Column(length = 10)
    private String apartment;

    @Column(nullable = false, length = 50)
    private String city;

    @Column(nullable = false, length = 8)
    private String postalCode;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Country country;

    @OneToOne(mappedBy = "address")
    private Customer customer;

    private boolean deleted = false;

    @Builder
    public Address(String street, String houseNumber, String apartment, String city, String postalCode, Country country) {
        this.street = street;
        this.houseNumber = houseNumber;
        this.apartment = apartment;
        this.city = city;
        this.postalCode = postalCode;
        this.country = country;
    }

    public String getFullAddress() {
        StringBuilder sb = new StringBuilder();

        if (street != null) {
            sb.append(street).append(" ");
        }
        if (houseNumber != null) {
            sb.append(houseNumber);
        }
        if (apartment != null && !apartment.isBlank()) {
            sb.append("-").append(apartment);
        }
        if (city != null) {
            sb.append(", ").append(city);
        }
        if (postalCode != null) {
            sb.append(", ").append(postalCode);
        }
        if (country != null) {
            sb.append(", ").append(country.name());
        }

        return sb.toString().trim();
    }
}
