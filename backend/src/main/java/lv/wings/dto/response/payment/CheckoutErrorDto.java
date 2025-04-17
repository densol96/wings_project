package lv.wings.dto.response.payment;

import java.util.List;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;
import lv.wings.enums.CheckoutStep;

@Getter
@Builder
public class CheckoutErrorDto {
    private String message;
    private CheckoutStep step;
    private String errorCode;
    private List<Integer> invalidIds;
    private Integer maxAmount;
    private Map<String, String> fieldErrors;
}
