package lv.wings.service;

import java.math.BigDecimal;
import lv.wings.dto.request.payment.NewCouponDto;
import lv.wings.dto.response.payment.CouponCodeDto;
import lv.wings.dto.response.payment.CouponDiscountDto;

public interface CouponService {
    CouponCodeDto createNewCoupon(NewCouponDto newCouponeDto);

    CouponDiscountDto applyCouponToCalculation(String code, BigDecimal orderTotal);
}
