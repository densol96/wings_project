package lv.wings.dto.response.delivery;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;
import lv.wings.enums.Country;
import lv.wings.enums.DeliveryMethod;

@Builder
@Getter
public class DeliveryDto {
    private Integer id; // refers to a Delivery Price
    private DeliveryMethod method;
    private String title;
    private String description;
    private BigDecimal price;
    private Country country;
}
