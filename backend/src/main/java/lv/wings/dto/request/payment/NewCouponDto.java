package lv.wings.dto.request.payment;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;

@Getter
@Setter
public class NewCouponDto {

    @DecimalMin(value = "0.0", inclusive = false, message = "{percentDiscount.min}")
    @DecimalMax(value = "100.0", inclusive = false, message = "{percentDiscount.max}")
    @Digits(integer = 3, fraction = 2, message = "{percentDiscount.format}")
    private BigDecimal percentDiscount;

    @DecimalMin(value = "0.0", inclusive = false, message = "{fixedDiscount.min}")
    @Digits(integer = 10, fraction = 2, message = "{fixedDiscount.format}")
    private BigDecimal fixedDiscount;

    @DecimalMin(value = "0.0", inclusive = false, message = "{minOrderTotal.min}")
    @Digits(integer = 10, fraction = 2, message = "{minOrderTotal.format}")
    private BigDecimal minOrderTotal;

    @FutureOrPresent(message = "{validFrom.future}")
    private LocalDateTime validFrom;

    @Future(message = "{validUntil.future}")
    private LocalDateTime validUntil;

    @Min(value = 1, message = "{usageLimit.min}")
    private Integer usageLimit;

    @AssertTrue(message = "{discount.required}")
    public boolean isAnyDiscountSet() {
        return percentDiscount != null || fixedDiscount != null;
    }
}

