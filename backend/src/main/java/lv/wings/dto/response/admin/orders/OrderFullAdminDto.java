package lv.wings.dto.response.admin.orders;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lv.wings.dto.response.payment.OrderSingleProductDto;
import lv.wings.enums.LocaleCode;

@Getter
@Setter
@Builder
public class OrderFullAdminDto {
    private Integer id; //
    private StatusDto status; //
    private FullDeliveryInfoDto deliveryInfo;
    private CustomerFullAdminDto customerInfo;
    private CouponAdminDto couponInfo;
    private BigDecimal total; //
    private LocaleCode locale; //
    private List<OrderSingleProductDto> items;
    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;
    private UserMinDto lastModifiedBy;
}
