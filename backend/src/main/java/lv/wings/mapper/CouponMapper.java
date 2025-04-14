package lv.wings.mapper;

import org.mapstruct.Mapper;
import lv.wings.dto.request.payment.NewCouponDto;
import lv.wings.model.entity.Coupon;

@Mapper(componentModel = "spring")
public interface CouponMapper {
    Coupon dtoToCoupon(NewCouponDto dto);
}
