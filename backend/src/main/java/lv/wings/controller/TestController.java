package lv.wings.controller;

import java.math.BigDecimal;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lv.wings.dto.request.payment.OrderDto;
import lv.wings.service.OrderService;
import lv.wings.service.PaymentService;

@Getter
class TestClass {
    private String PRIVATE_FIELD = "PRIVATE_FIELD";
    public String PUBLIC_FIELD = "PUBLIC_FIELD";
    String FIELD = "FIELD";
}


@RestController
@RequiredArgsConstructor
public class TestController {

    private final OrderService orderService;
    private final PaymentService paymentService;

    @PostMapping("/test")
    public String test(@RequestBody @Valid OrderDto dto) {
        // orderService.saveNewOrder(dto, "test_payment_indenыыt", BigDecimal.valueOf(10), BigDecimal.valueOf(30));
        return paymentService.initPayment(dto).getClientSecret();

    }
}
