package lv.wings.controller.open;

import java.math.BigDecimal;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import lv.wings.dto.request.payment.NewCouponDto;
import lv.wings.dto.response.payment.CouponCodeDto;
import lv.wings.dto.response.payment.CouponDiscountDto;
import lv.wings.service.CouponService;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/coupons")
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;

    @PostMapping
    public ResponseEntity<CouponCodeDto> createCoupon(@Valid @RequestBody NewCouponDto newCouponDto) {
        log.info("Received POST request on /api/v1/coupons");
        return ResponseEntity.ok().body(couponService.createNewCoupon(newCouponDto));
    }

    @GetMapping("/calc")
    public ResponseEntity<CouponDiscountDto> calculateDiscount(
            @RequestParam BigDecimal total,
            @RequestParam String code) {
        log.info("Received GET request on /api/v1/coupons/calc with total of {} and code of {}.", total, code);
        return ResponseEntity.ok().body(couponService.applyCouponToCalculation(code, total));
    }
}
