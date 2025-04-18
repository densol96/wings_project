package lv.wings.dto.response.payment;

import java.math.BigDecimal;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class OrderSummaryDto {
    private Integer id;
    private String deliverySumup;
    private Integer discount;
    private List<OrderSingleProductDto> items;
    private BigDecimal total;
    private String firstName;
    private String lastName;
    private String email;
}
