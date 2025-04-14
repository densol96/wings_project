package lv.wings.service;

import lv.wings.dto.request.payment.OrderDto;
import lv.wings.dto.response.payment.PaymentIntentDto;

public interface PaymentService {
    PaymentIntentDto createPaymentIntent(OrderDto orderDto);
}
