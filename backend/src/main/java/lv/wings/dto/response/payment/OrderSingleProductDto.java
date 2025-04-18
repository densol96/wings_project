package lv.wings.dto.response.payment;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class OrderSingleProductDto {
    private Integer productId;
    private String name;
    private Integer amount;
    private BigDecimal price;
}
