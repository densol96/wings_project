package lv.wings.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lv.wings.dto.request.payment.AddressDto;
import lv.wings.dto.request.payment.OrderDto;
import lv.wings.dto.response.payment.OrderItemDto;
import lv.wings.dto.response.payment.PaymentIntentDto;
import lv.wings.enums.DeliveryMethod;
import lv.wings.exception.coupon.InvalidCouponException;
import lv.wings.exception.entity.EntityNotFoundException;
import lv.wings.exception.payment.FailedIntentException;
import lv.wings.model.entity.DeliveryPrice;
import lv.wings.model.entity.Product;
import lv.wings.service.CouponService;
import lv.wings.service.DeliveryPriceService;
import lv.wings.service.OmnivaService;
import lv.wings.service.OrderService;
import lv.wings.service.PaymentService;
import lv.wings.service.ProductService;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final ProductService productService;
    private final DeliveryPriceService deliveryPriceService;
    private final OmnivaService terminalService;
    private final CouponService couponService;
    private final OrderService orderService;


    private BigDecimal validateOrderItemsInput(List<OrderItemDto> orderItemDtos) {

        List<Integer> productIds = orderItemDtos.stream().map(item -> item.getProductId()).toList();
        Map<Integer, Product> productMap = productService.getProductsByIds(productIds).stream()
                .collect(Collectors.toMap(Product::getId, Function.identity()));

        // (X) Mismatch between provided ids and found products => less products found than ids provided
        if (productIds.size() != productMap.values().size()) {
            List<Integer> invalidItemIds = productIds.stream().filter(id -> !productMap.containsKey(id)).toList();

            throw FailedIntentException.builder()
                    .message("Payment intent creation failed due to unrecognized order items.")
                    .invalidItemIds(invalidItemIds)
                    .nameKey("payment-intent.invalid.unknown-items")
                    .build();
        }

        return orderItemDtos.stream()
                .map(item -> {
                    Product product = productMap.values().stream()
                            .filter(p -> p.getId() == item.getProductId())
                            .findFirst().get(); // this is safe to do cause we did the list validation at the very beginning
                    if (product.getAmount() < item.getAmount()) {
                        throw FailedIntentException.builder()
                                .message("Payment intent creation failed due to requested quantity exceeding available stock.")
                                .invalidItemIds(List.of(item.getProductId()))
                                .maxAmount(product.getAmount())
                                .nameKey("payment-intent.invalid.product-amount-exceeded")
                                .build();
                    }
                    return product.getPrice().multiply(BigDecimal.valueOf(item.getAmount()));
                })
                // same as (a, b) -> a.add(b) according to docs: API Note:
                // Sum, min, max, average, and string concatenation are all special cases of reduction. Summing a stream of numbers can be expressed as:
                // Integer sum = integers.reduce(0, Integer::sum);
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal validateDeliveryTypeInput(Integer deliveryPriceId, Integer terminalId, AddressDto address) {
        DeliveryPrice deliveryMethodVariation;
        try {
            deliveryMethodVariation = deliveryPriceService.findById(deliveryPriceId);
        } catch (EntityNotFoundException e) {
            throw FailedIntentException.builder()
                    .message("Payment intent creation failed due to unrecognized delivery variation (deliveryPrice)")
                    .nameKey("payment-intent.invalid.unknown-delivery-price")
                    .build();
        }


        DeliveryMethod deliveryMethod = deliveryMethodVariation.getDeliveryType().getMethod();

        if (deliveryMethod == DeliveryMethod.PARCEL_MACHINE && terminalId == null
                || deliveryMethod == DeliveryMethod.PARCEL_MACHINE && !terminalService.existsById(terminalId)) {
            throw FailedIntentException.builder()
                    .message("Payment intent creation failed due to unrecognized terminal")
                    .nameKey("payment-intent.invalid.unknown-terminal")
                    .build();
        }

        if (deliveryMethod == DeliveryMethod.COURIER && address == null) {
            throw FailedIntentException.builder()
                    .message("Payment intent creation failed due to missing addresss required for a courier delivery")
                    .nameKey("payment-intent.invalid.missing-address")
                    .build();
        }

        if (deliveryMethod == DeliveryMethod.COURIER && deliveryMethodVariation.getCountry() != address.getCountry()) {
            throw FailedIntentException.builder()
                    .message("Payment intent creation failed due to country in the addresss and in the delivery variation (delivery Price) not matching")
                    .nameKey("payment-intent.invalid.address-missmatch")
                    .build();
        }

        return deliveryMethodVariation.getPrice();
    }

    private BigDecimal validateDiscount(String couponCode, BigDecimal totalProductsCost) {
        BigDecimal discount;
        try {
            discount = couponService.applyCouponToCalculation(couponCode, totalProductsCost).getDiscount();
        } catch (NullPointerException | InvalidCouponException e) {
            discount = BigDecimal.ZERO;
        }
        return discount;
    }

    private Long validateTotalCostsAndReturnInCents(BigDecimal totalCosts, BigDecimal orderTotalDto) {
        FailedIntentException totalCostsProblem = FailedIntentException.builder()
                .message("Payment intent creation failed due to final costs not matching")
                .nameKey("payment-intent.invalid.total-costs")
                .build();

        System.out.println(totalCosts);
        System.out.println(orderTotalDto);

        if (totalCosts.compareTo(orderTotalDto) != 0) {
            throw totalCostsProblem;
        }

        try {
            return totalCosts.movePointRight(2)
                    .setScale(0, RoundingMode.HALF_UP)
                    .longValueExact();
        } catch (ArithmeticException e) {
            throw totalCostsProblem;
        }
    }

    private PaymentIntent createPaymentIntent(Long cents) {
        try {
            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                    .setAmount(cents)
                    .setCurrency("eur")
                    .setAutomaticPaymentMethods(
                            PaymentIntentCreateParams.AutomaticPaymentMethods.builder().setEnabled(true).build())
                    .build();

            return PaymentIntent.create(params);


        } catch (StripeException e) {
            throw FailedIntentException.builder()
                    .message("Payment intent creation failed due to StripeException")
                    .nameKey("payment-intent.invalid.stipe-service")
                    .build();
        }
    }

    @Override
    @Transactional
    public PaymentIntentDto initPayment(OrderDto orderDto) {

        BigDecimal productsTotalCost = validateOrderItemsInput(orderDto.getOrderItems());

        BigDecimal deliveryPrice =
                validateDeliveryTypeInput(orderDto.getDeliveryMethodVariationId(), orderDto.getTerminalId(), orderDto.getCustomerInfo().getAddress());

        BigDecimal discount = validateDiscount(orderDto.getCouponCode(), productsTotalCost);

        BigDecimal totalCosts = productsTotalCost.add(deliveryPrice).subtract(discount);

        System.out.println(productsTotalCost);
        System.out.println(deliveryPrice);
        System.out.println(discount);
        System.out.println(totalCosts);

        Long cents = validateTotalCostsAndReturnInCents(totalCosts, orderDto.getTotal());

        PaymentIntent paymentIntent = createPaymentIntent(cents);

        orderService.saveNewOrder(orderDto, paymentIntent.getId(), discount, totalCosts);

        return new PaymentIntentDto(paymentIntent.getClientSecret());
    }
}
