package lv.wings.service.impl;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;
import io.micrometer.common.lang.NonNull;
import lombok.RequiredArgsConstructor;
import lv.wings.dto.request.payment.NewCouponDto;
import lv.wings.dto.response.payment.CouponCodeDto;
import lv.wings.dto.response.payment.CouponDiscountDto;
import lv.wings.exception.coupon.InvalidCouponException;
import lv.wings.mapper.CouponMapper;
import lv.wings.model.entity.Coupon;
import lv.wings.repo.CouponRepository;
import lv.wings.service.CouponService;

@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {

    private final CouponRepository couponRepository;
    private final CouponMapper couponMapper;


    @Override
    public CouponCodeDto createNewCoupon(@NonNull NewCouponDto newCouponeDto) {
        Coupon newCoupon = couponMapper.dtoToCoupon(newCouponeDto);
        newCoupon.setCode(generateCouponCode(Coupon.CODE_LENGTH));
        couponRepository.save(newCoupon);
        return new CouponCodeDto(newCoupon.getCode());
    }

    @Override
    public CouponDiscountDto applyCouponToCalculation(@NonNull String code, @NonNull BigDecimal orderTotal) {
        Coupon coupon = validateCoupon(code, orderTotal);
        return new CouponDiscountDto(calculateDiscount(coupon, orderTotal));
    }

    private String generateCouponCode(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }

    private boolean isCouponValidNow(Coupon coupon) {
        LocalDateTime now = LocalDateTime.now();

        return coupon.isActive()
                && (coupon.getValidFrom() == null || !now.isBefore(coupon.getValidFrom()))
                && (coupon.getValidUntil() == null || !now.isAfter(coupon.getValidUntil()))
                && (coupon.getUsageLimit() == null || coupon.getUsedCount() < coupon.getUsageLimit());
    }

    private Coupon validateCoupon(String code, BigDecimal orderTotal) {
        Coupon coupon = couponRepository.findByCodeIgnoreCase(code)
                .orElseThrow(() -> new InvalidCouponException("coupon.invalid"));

        if (!isCouponValidNow(coupon)) {
            throw new InvalidCouponException("coupon.invalid");
        }

        if (coupon.getMinOrderTotal() != null && orderTotal.compareTo(coupon.getMinOrderTotal()) < 0) {
            throw new InvalidCouponException(coupon.getMinOrderTotal());
        }

        return coupon;
    }

    private BigDecimal calculateDiscount(Coupon coupon, BigDecimal baseAmount) {
        if (coupon.getFixedDiscount() != null) {
            return coupon.getFixedDiscount().min(baseAmount);
        }

        if (coupon.getPercentDiscount() != null) {
            return baseAmount
                    .multiply(coupon.getPercentDiscount())
                    .divide(BigDecimal.valueOf(100));
        }

        return BigDecimal.ZERO;
    }

    private void incrementUsage(Coupon coupon) {
        if (coupon.getUsageLimit() != null) {
            coupon.setUsedCount(coupon.getUsedCount() + 1);
            couponRepository.save(coupon);
        }
    }
}
