package lv.wings.dto.request.payment;

import java.math.BigDecimal;
import java.util.List;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderDto {
    @NotNull(message = "{order.items.required}")
    @Valid
    private List<OrderItemDto> orderItems;

    @NotNull(message = "{delivery.method-price.required}")
    private Integer deliveryMethodVariationId; // deliveryPrice attached to a deliveryType

    private Integer terminalId; // only if the deliveryType is omniva

    @NotNull(message = "{customer.info.required}")
    @Valid
    private CustomerDataDto customerInfo;

    private String couponCode;

    @NotNull(message = "{total.required}")
    @Positive(message = "{total.positive}")
    private BigDecimal total; // products + delivery - discount
}
