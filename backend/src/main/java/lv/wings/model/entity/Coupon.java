package lv.wings.model.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lv.wings.model.base.AuditableEntityExtended;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "coupons")
@NoArgsConstructor
public class Coupon extends AuditableEntityExtended {

    @Column(nullable = false, unique = true, length = 50)
    private String code;

    @Column(nullable = false)
    private boolean active = true;

    @Column(precision = 4, scale = 2) // up till 99.99
    private BigDecimal percentDiscount;

    @Column(precision = 5, scale = 2) // 999.99
    private BigDecimal fixedDiscount;

    @Column(precision = 6, scale = 2)
    private BigDecimal minOrderTotal;

    private LocalDateTime validFrom;

    private LocalDateTime validUntil;

    private Integer usageLimit;

    private Integer usedCount = 0;

    public final static int CODE_LENGTH = 8;

    @Builder
    public Coupon(
            String code,
            BigDecimal percentDiscount,
            BigDecimal fixedDiscount,
            BigDecimal minOrderTotal,
            LocalDateTime validFrom,
            LocalDateTime validUntil,
            Integer usageLimit) {
        this.code = code;
        this.active = true;
        this.percentDiscount = percentDiscount;
        this.fixedDiscount = fixedDiscount;
        this.minOrderTotal = minOrderTotal;
        this.validFrom = validFrom;
        this.validUntil = validUntil;
        this.usageLimit = usageLimit;
        this.usedCount = 0;
    }
}
