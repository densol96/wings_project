package lv.wings.exception.payment;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class FailedIntentException extends RuntimeException {

    private String nameKey;
    private List<Integer> invalidItemIds;
    private Integer maxAmount;

    @Builder
    public FailedIntentException(String message, String nameKey, List<Integer> invalidItemIds, Integer maxAmount) {
        super(message);
        this.nameKey = nameKey;
        this.invalidItemIds = invalidItemIds;
        this.maxAmount = maxAmount;
    }
}
