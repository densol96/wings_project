package lv.wings.model.entity;

import java.util.Objects;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lv.wings.enums.Country;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "terminals")
@Data
@NoArgsConstructor
@Where(clause = "deleted = false")
@SQLDelete(sql = "UPDATE terminals SET deleted = true WHERE id = ?")
public class Terminal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(value = AccessLevel.NONE)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String zip;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private Integer type;

    @Enumerated(EnumType.STRING)
    private Country country;

    @Column(nullable = false, unique = true)
    private String address;

    @Column(nullable = false)
    private Double xCoordinate;

    @Column(nullable = false)
    private Double yCoordinate;

    private boolean deleted = false;

    @Builder
    public Terminal(
            String zip,
            String name,
            Integer type,
            Country country,
            String address,
            Double xCoordinate,
            Double yCoordinate) {
        this.zip = zip;
        this.name = name;
        this.type = type;
        this.country = country;
        this.address = address;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Terminal terminal = (Terminal) o;

        return Objects.equals(zip, terminal.zip) &&
                Objects.equals(name, terminal.name) &&
                type == terminal.type &&
                country == terminal.country &&
                Objects.equals(address, terminal.address) &&
                Double.compare(xCoordinate, terminal.xCoordinate) == 0 &&
                Double.compare(yCoordinate, terminal.yCoordinate) == 0;
    }

    public void updateSelf(Terminal newVersion) {
        this.name = newVersion.getName();
        this.type = newVersion.getType();
        this.country = newVersion.getCountry();
        this.address = newVersion.getAddress();
        this.xCoordinate = newVersion.getXCoordinate();
        this.yCoordinate = newVersion.getYCoordinate();
    }
}
