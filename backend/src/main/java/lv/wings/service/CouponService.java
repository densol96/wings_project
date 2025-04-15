package lv.wings.service;

import java.math.BigDecimal;
import lv.wings.dto.request.payment.NewCouponDto;
import lv.wings.dto.response.payment.CouponCodeDto;
import lv.wings.dto.response.payment.CouponDiscountDto;
import lv.wings.exception.coupon.InvalidCouponException;
import lv.wings.model.entity.Coupon;

public interface CouponService extends CRUDService<Coupon, Integer> {
    CouponCodeDto createNewCoupon(NewCouponDto newCouponeDto);

    /**
     * Validates the coupon code and applies the coupon to the provided order total
     *
     * @param code the code of the coupon to apply; must not be null
     * @param orderTotal the amount the coupon will be applied to; must not be null
     * @throws NullPointerException if {@code code} is null
     * @throws InvalidCouponException if the coupon code is invalid or expired or does not exist in the DB
     */
    CouponDiscountDto applyCouponToCalculation(String code, BigDecimal orderTotal);

    Coupon findByCode(String code);
}
