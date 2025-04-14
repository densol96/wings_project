package lv.wings.dto.response.payment;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PaymentIntentDto {
    private String clientSecret;
}
