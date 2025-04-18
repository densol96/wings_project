package lv.wings.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lv.wings.dto.response.payment.OrderSummaryDto;
import lv.wings.service.OrderService;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/post-payment/{paymentIntentId}")
    public ResponseEntity<OrderSummaryDto> getPostPaymentOrderSummary(@PathVariable String paymentIntentId) {
        return ResponseEntity.ok().body(orderService.getOrderSummary(paymentIntentId));
    }
}
