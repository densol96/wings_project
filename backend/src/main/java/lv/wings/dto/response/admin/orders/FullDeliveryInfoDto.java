package lv.wings.dto.response.admin.orders;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lv.wings.enums.Country;
import lv.wings.enums.DeliveryMethod;

@Getter
@Setter
@Builder
public class FullDeliveryInfoDto {
    private Integer id; //
    private DeliveryMethod methodCode;
    private String methodName;
    private Country country; //
    private BigDecimal price; //
    private BigDecimal deliveryPriceAtOrderTime;
}
