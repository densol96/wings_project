package lv.wings.service.impl;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;
import lombok.NonNull;
import lv.wings.dto.request.payment.NewCouponDto;
import lv.wings.dto.response.admin.orders.CouponAdminDto;
import lv.wings.dto.response.payment.CouponCodeDto;
import lv.wings.dto.response.payment.CouponDiscountDto;
import lv.wings.exception.coupon.InvalidCouponException;
import lv.wings.mapper.CouponMapper;
import lv.wings.model.entity.Coupon;
import lv.wings.model.entity.Order;
import lv.wings.repo.CouponRepository;
import lv.wings.service.AbstractCRUDService;
import lv.wings.service.CouponService;

@Service
public class CouponServiceImpl extends AbstractCRUDService<Coupon, Integer> implements CouponService {

    private final CouponRepository couponRepository;
    private final CouponMapper couponMapper;

    public CouponServiceImpl(CouponRepository couponRepository, CouponMapper couponMapper) {
        super(couponRepository, "Coupon", "entity.coupon");
        this.couponRepository = couponRepository;
        this.couponMapper = couponMapper;
    }

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


    @Override
    public Coupon findByCode(String code) {
        return couponRepository.findByCodeIgnoreCase(code).orElse(null);
    }

    @Override
    public CouponAdminDto orderToCouponAdminDto(@NonNull Order order) {
        Coupon coupon = order.getAppliedCoupon();
        if (coupon == null)
            return null;
        return couponMapper.toAdminDto(coupon, order.getDiscountAtOrderTime());
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
