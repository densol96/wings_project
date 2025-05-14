package lv.wings.model.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lv.wings.enums.Country;

@Entity
@Table(name = "delivery_type_prices")
@Getter
@Setter
@NoArgsConstructor
public class DeliveryPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "delivery_type_id", nullable = false)
    private DeliveryType deliveryType;

    @Enumerated(EnumType.STRING)
    private Country country;

    @Column(nullable = true)
    private BigDecimal price;

    @OneToMany
    private List<Order> orders = new ArrayList<>();

    @Builder
    public DeliveryPrice(Country country, BigDecimal price, DeliveryType deliveryType) {
        this.country = country;
        this.price = price;
        this.deliveryType = deliveryType;
    }
}
