package lv.wings.mapper;

import java.math.BigDecimal;
import org.mapstruct.Mapper;
import lv.wings.dto.request.payment.NewCouponDto;
import lv.wings.dto.response.admin.orders.CouponAdminDto;
import lv.wings.model.entity.Coupon;

@Mapper(componentModel = "spring")
public interface CouponMapper {
    Coupon dtoToCoupon(NewCouponDto dto);

    CouponAdminDto toAdminDto(Coupon coupon, BigDecimal discountAtOrderTime);
}
