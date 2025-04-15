package lv.wings.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stripe.Stripe;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lv.wings.dto.request.payment.OrderDto;
import lv.wings.dto.response.payment.PaymentIntentDto;
import lv.wings.service.PaymentService;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    @Value("${stripe.secret-key}")
    private String stripeSecretKey;

    private final PaymentService paymentService;

    @PostConstruct
    public void init() {
        Stripe.apiKey = stripeSecretKey;
    }

    @PostMapping("/create-payment-intent")
    public ResponseEntity<PaymentIntentDto> createPaymentIntent(OrderDto orderDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(paymentService.initPayment(orderDto));
    }
}
