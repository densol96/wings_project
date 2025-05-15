package lv.wings.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import lv.wings.service.OrderService;
import lv.wings.service.PaymentService;
import lv.wings.service.TranslationService;

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
    private final TranslationService translationService;

    @GetMapping("/test")
    public String test(@RequestParam String text) {
        // orderService.saveNewOrder(dto, "test_payment_indenыыt", BigDecimal.valueOf(10), BigDecimal.valueOf(30));
        log.info("THE CONTROLLER METHOD ACTUALLY RUNS!");
        return translationService.translateToEnglish(text);
    }
}
