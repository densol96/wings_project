package lv.wings.model.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lv.wings.model.base.AuditableEntityExtended;
import java.math.BigDecimal;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "coupons")
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

    @Builder
    public Coupon(BigDecimal percentDiscount,
            BigDecimal fixedDiscount,
            BigDecimal minOrderTotal,
            LocalDateTime validFrom,
            LocalDateTime validUntil,
            Integer usageLimit) {
        this.code = generateCouponCode(10);
        this.active = true;
        this.percentDiscount = percentDiscount;
        this.fixedDiscount = fixedDiscount;
        this.minOrderTotal = minOrderTotal;
        this.validFrom = validFrom;
        this.validUntil = validUntil;
        this.usageLimit = usageLimit;
        this.usedCount = 0;
    }

    private String generateCouponCode(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }

    public boolean isValidNow() {
        LocalDateTime now = LocalDateTime.now();
        return active &&
                (validFrom == null || !now.isBefore(validFrom)) &&
                (validUntil == null || !now.isAfter(validUntil)) &&
                (usageLimit == null || usedCount < usageLimit);
    }

    public BigDecimal calculateDiscount(BigDecimal orderTotal) {
        if (!isValidNow() || (minOrderTotal != null && orderTotal.compareTo(minOrderTotal) < 0)) {
            return BigDecimal.ZERO;
        }

        if (fixedDiscount != null) {
            return fixedDiscount.min(orderTotal);
        }

        if (percentDiscount != null) {
            return orderTotal.multiply(percentDiscount).divide(BigDecimal.valueOf(100));
        }

        return BigDecimal.ZERO;
    }
}
