package lv.wings.service;

import org.springframework.validation.BindingResult;
import lv.wings.dto.request.payment.OrderDto;
import lv.wings.dto.response.payment.PaymentIntentDto;

public interface PaymentService {
    PaymentIntentDto initPayment(OrderDto orderDto);

    void parseDtoValidationProblems(BindingResult bindingResult);

    String handleStripeWebhook(String payload, String sigHeader);
}
