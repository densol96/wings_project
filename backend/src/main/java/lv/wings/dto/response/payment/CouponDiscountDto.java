package lv.wings.dto.response.payment;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CouponDiscountDto {
    private BigDecimal discount;
}
