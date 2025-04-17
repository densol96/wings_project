package lv.wings.exception.payment;

import java.util.List;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;
import lv.wings.enums.CheckoutStep;

@Getter
public class CheckoutException extends RuntimeException {

    private CheckoutStep step;
    private String errorCode;
    private List<Integer> invalidIds;
    private Integer maxAmount;
    private Map<String, String> fieldErrors;

    @Builder
    public CheckoutException(String logMessage, CheckoutStep step, String errorCode, List<Integer> invalidIds, Integer maxAmount,
            Map<String, String> fieldErrors) {
        super(logMessage);
        this.invalidIds = invalidIds;
        this.maxAmount = maxAmount;
        this.step = step;
        this.errorCode = errorCode;
        this.fieldErrors = fieldErrors;
    }
}
