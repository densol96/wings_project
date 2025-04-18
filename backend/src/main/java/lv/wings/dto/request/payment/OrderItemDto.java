package lv.wings.dto.request.payment;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemDto {
    @NotNull(message = "{product.id.required}")
    @JsonProperty("id")
    private Integer productId;

    @NotNull(message = "{amount.required}")
    @Min(value = 1, message = "{amount.min}")
    private Integer amount;
}
