package lv.wings.controller;

import java.math.BigDecimal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lv.wings.dto.request.payment.OrderDto;
import lv.wings.service.OrderService;
import lv.wings.service.PaymentService;

@Getter
class TestClass {
    private String PRIVATE_FIELD = "PRIVATE_FIELD";
    public String PUBLIC_FIELD = "PUBLIC_FIELD";
    String FIELD = "FIELD";
}


@Slf4j
@RestController
@RequiredArgsConstructor
public class TestController {

    private final OrderService orderService;
    private final PaymentService paymentService;

    @GetMapping("/test")
    public String test() {
        // orderService.saveNewOrder(dto, "test_payment_indenыыt", BigDecimal.valueOf(10), BigDecimal.valueOf(30));
        log.info("THE CONTROLLER METHOD ACTUALLY RUNS!");
        return "HELLO FROM SPRING!";

    }
}
