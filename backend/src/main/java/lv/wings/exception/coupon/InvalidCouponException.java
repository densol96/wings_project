package lv.wings.exception.coupon;

import java.math.BigDecimal;
import lombok.Getter;

@Getter
public class InvalidCouponException extends RuntimeException {

    private final String messageCode;
    private final BigDecimal minAmount;

    public static final String DEFAULT_CODE = "coupon.invalid";
    public static final String MIN_AMOUNT_CODE = "coupon.min.amount";

    public InvalidCouponException(String messageCode) {
        super("Invalid coupon");
        this.messageCode = messageCode != null && !messageCode.equals(MIN_AMOUNT_CODE) ? messageCode : DEFAULT_CODE;
        this.minAmount = null;
    }

    public InvalidCouponException(BigDecimal minAmount) {
        super("Order total is below required amount for coupon.");
        this.messageCode = MIN_AMOUNT_CODE;
        this.minAmount = minAmount;
    }
}
