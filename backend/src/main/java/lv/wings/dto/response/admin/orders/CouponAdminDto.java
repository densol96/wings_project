package lv.wings.dto.response.admin.orders;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CouponAdminDto {
    private Integer id;
    private String code;
    private BigDecimal percentDiscount;
    private BigDecimal fixedDiscount;
    private BigDecimal discountAtOrderTime;
}
