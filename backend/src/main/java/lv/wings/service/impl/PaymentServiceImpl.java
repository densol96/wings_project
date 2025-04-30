package lv.wings.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.net.Webhook;
import com.stripe.param.PaymentIntentCreateParams;

import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lv.wings.dto.request.payment.AddressDto;
import lv.wings.dto.request.payment.OrderDto;
import lv.wings.dto.request.payment.OrderItemDto;
import lv.wings.dto.response.payment.PaymentIntentDto;
import lv.wings.enums.CheckoutStep;
import lv.wings.enums.DeliveryMethod;
import lv.wings.enums.OrderStatus;
import lv.wings.exception.coupon.InvalidCouponException;
import lv.wings.exception.entity.EntityNotFoundException;
import lv.wings.exception.payment.CheckoutException;
import lv.wings.exception.payment.WebhookException;
import lv.wings.model.entity.DeliveryPrice;
import lv.wings.model.entity.Product;
import lv.wings.service.CouponService;
import lv.wings.service.DeliveryPriceService;
import lv.wings.service.OmnivaService;
import lv.wings.service.OrderService;
import lv.wings.service.PaymentService;
import lv.wings.service.ProductService;
import lv.wings.service.scheduler.OrderTimeoutScheduler;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    private final ProductService productService;
    private final DeliveryPriceService deliveryPriceService;
    private final OmnivaService terminalService;
    private final CouponService couponService;
    private final OrderService orderService;
    private final OrderTimeoutScheduler orderTimeoutScheduler;

    @Value("${stripe.webhook.secret}")
    private String endpointSecret;

    private BigDecimal validateOrderItemsInput(List<OrderItemDto> orderItemDtos) {

        List<Integer> productIds = orderItemDtos.stream().map(item -> item.getProductId()).toList();
        Map<Integer, Product> productMap = productService.getProductsByIdsWithLock(productIds).stream()
                .collect(Collectors.toMap(Product::getId, Function.identity()));

        // (X) Mismatch between provided ids and found products => less products found
        // than ids provided
        if (productIds.size() != productMap.values().size()) {
            List<Integer> invalidIds = productIds.stream().filter(id -> !productMap.containsKey(id)).toList();

            throw CheckoutException.builder()
                    .logMessage("Payment intent creation failed due to unrecognized order items.")
                    .step(CheckoutStep.CART)
                    .errorCode("unknown-items")
                    .invalidIds(invalidIds)
                    .build();
        }

        return orderItemDtos.stream()
                .map(item -> {
                    Product product = productMap.get(item.getProductId());
                    if (product.getAmount() < item.getAmount()) {
                        throw CheckoutException.builder()
                                .logMessage(
                                        "Payment intent creation failed due to requested quantity exceeding available stock.")
                                .step(CheckoutStep.CART)
                                .errorCode("product-amount-exceeded")
                                .invalidIds(List.of(item.getProductId()))
                                .maxAmount(product.getAmount())
                                .build();
                    }
                    product.setAmount(product.getAmount() - item.getAmount());
                    productService.create(product);
                    return product.getPrice().multiply(BigDecimal.valueOf(item.getAmount()));
                })
                // same as (a, b) -> a.add(b) according to docs: API Note:
                // Sum, min, max, average, and string concatenation are all special cases of
                // reduction. Summing a stream of numbers can be expressed as:
                // Integer sum = integers.reduce(0, Integer::sum);
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal validateDeliveryTypeInput(Integer deliveryPriceId, Integer terminalId, AddressDto address) {
        DeliveryPrice deliveryMethodVariation;
        try {
            deliveryMethodVariation = deliveryPriceService.findById(deliveryPriceId);
        } catch (EntityNotFoundException e) {
            throw CheckoutException.builder()
                    .logMessage("Payment intent creation failed due to unrecognized delivery variation (deliveryPrice)")
                    .step(CheckoutStep.DELIVERY)
                    .errorCode("unknown-delivery-price")
                    .build();
        }

        DeliveryMethod deliveryMethod = deliveryMethodVariation.getDeliveryType().getMethod();

        if (deliveryMethod == DeliveryMethod.PARCEL_MACHINE && terminalId == null
                || deliveryMethod == DeliveryMethod.PARCEL_MACHINE && !terminalService.existsById(terminalId)) {
            throw CheckoutException.builder()
                    .logMessage("Payment intent creation failed due to unrecognized terminal")
                    .step(CheckoutStep.DELIVERY)
                    .errorCode("unknown-terminal")
                    .build();
        }

        if (deliveryMethod == DeliveryMethod.COURIER && address == null) {
            throw CheckoutException.builder()
                    .logMessage(
                            "Payment intent creation failed due to missing addresss required for a courier delivery")
                    .step(CheckoutStep.DELIVERY)
                    .errorCode("missing-address")
                    .build();
        }

        if (deliveryMethod == DeliveryMethod.COURIER && deliveryMethodVariation.getCountry() != address.getCountry()) {
            throw CheckoutException.builder()
                    .logMessage(
                            "Payment intent creation failed due to country in the addresss and in the delivery variation (delivery Price) not matching")
                    .step(CheckoutStep.DELIVERY)
                    .errorCode("address-missmatch")
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
        CheckoutException totalCostsProblem = CheckoutException.builder()
                .logMessage("Payment intent creation failed due to final costs not matching")
                .step(CheckoutStep.CART)
                .errorCode("total-costs")
                .build();

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
                    .addPaymentMethodType("card")
                    .addPaymentMethodType("sepa_debit")
                    .addPaymentMethodType("paypal")
                    // .setAutomaticPaymentMethods(
                    // PaymentIntentCreateParams.AutomaticPaymentMethods.builder().setEnabled(true).build())
                    .build();

            return PaymentIntent.create(params);

        } catch (StripeException e) {
            throw CheckoutException.builder()
                    .logMessage("Payment intent creation failed due to StripeException")
                    .step(CheckoutStep.SERVER)
                    .errorCode("stripe-service")
                    .build();
        }
    }

    @Override
    public void parseDtoValidationProblems(BindingResult bindingResult) {
        Map<String, String> formFieldErrors = new HashMap<>();

        String generalErrorMessage = null;
        CheckoutStep generalErrorStep = null;
        String generalErrorCode = null;

        for (FieldError error : bindingResult.getFieldErrors()) {
            String field = error.getField();
            if (field.startsWith("customerInfo")) {
                if (field.startsWith("customerInfo.address.postalAndCountryMatch")) {
                    formFieldErrors.put("address.postalCode", error.getDefaultMessage());
                } else if (field.startsWith("customerInfo.phoneAndCountryMatch")) {
                    formFieldErrors.put("phoneNumber", error.getDefaultMessage());
                } else {
                    formFieldErrors.put(field.replace("customerInfo.", ""), error.getDefaultMessage());
                }
            } else if (field.startsWith("orderItems") || "total".equals(field)) {
                System.out.println(error.getDefaultMessage());
                generalErrorStep = CheckoutStep.CART;
                generalErrorCode = "cart-problem";
                generalErrorMessage = "There is an issue with your order items or total.";
            } else if (field.startsWith("deliveryMethodVariationId") || field.startsWith("terminalId")) {
                generalErrorStep = CheckoutStep.DELIVERY;
                generalErrorCode = "delivery-problem";
                generalErrorMessage = "Delivery method is not properly selected.";
            } else {
                generalErrorStep = CheckoutStep.SERVER;
                generalErrorCode = "internal-problem";
                generalErrorMessage = "There is an issue with they way server handles the dto validation logic?";
            }
        }

        if (!formFieldErrors.isEmpty()) {
            throw CheckoutException.builder()
                    .logMessage("Customer info form validation failed")
                    .step(CheckoutStep.FORM)
                    .errorCode("form-field")
                    .fieldErrors(formFieldErrors)
                    .build();
        } else {
            throw CheckoutException.builder()
                    .logMessage(generalErrorMessage)
                    .step(generalErrorStep)
                    .errorCode(generalErrorCode)
                    .build();
        }
    }

    @Override
    @Transactional
    public PaymentIntentDto initPayment(OrderDto orderDto) {

        BigDecimal productsTotalCost = validateOrderItemsInput(orderDto.getOrderItems());

        BigDecimal deliveryPrice = validateDeliveryTypeInput(orderDto.getDeliveryMethodVariationId(),
                orderDto.getTerminalId(), orderDto.getCustomerInfo().getAddress());

        BigDecimal discount = validateDiscount(orderDto.getCouponCode(), productsTotalCost);

        BigDecimal totalCosts = productsTotalCost.add(deliveryPrice).subtract(discount);

        Long cents = validateTotalCostsAndReturnInCents(totalCosts, orderDto.getTotal());

        PaymentIntent paymentIntent = createPaymentIntent(cents);

        orderService.saveNewOrder(orderDto, paymentIntent.getId(), discount, totalCosts);

        orderTimeoutScheduler.scheduleOrderTimeoutCheck(paymentIntent.getId(), Duration.ofSeconds(30));

        return new PaymentIntentDto(paymentIntent.getClientSecret());
    }

    @Override
    public String handleStripeWebhook(String payload, String sigHeader) {
        Event event;

        try {
            event = Webhook.constructEvent(payload, sigHeader, endpointSecret);
        } catch (SignatureVerificationException e) {
            // Подпись неверна — не доверяем
            throw new WebhookException("Invalid signature");
        }

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode;
        try {
            rootNode = objectMapper.readTree(event.getDataObjectDeserializer().getRawJson());
        } catch (Exception e) {
            throw new WebhookException("Unable to deserialise the event.getObject");
        }

        String paymentIntentId = rootNode.get("id").asText();

        switch (event.getType()) {
            case "payment_intent.succeeded" -> {
                log.info("Received payment_intent.succeeded on the Stripe WebHook");
                orderService.updateOrderStatusOnWebhookEvent(paymentIntentId, OrderStatus.PAID); // also sends email
            }
            case "payment_intent.canceled" -> {
                log.info("Received payment_intent.canceled on the Stripe WebHook");
                orderService.updateOrderStatusOnWebhookEvent(paymentIntentId, OrderStatus.CANCELLED);
            }
            case "payment_intent.payment_failed" -> {
                log.info("Received payment_intent.payment_failed on the Stripe WebHook");
                orderService.updateOrderStatusOnWebhookEvent(paymentIntentId, OrderStatus.FAILED);
            }
            default -> log.info("Unhandled WebHook event type: " + event.getType());
        }

        return "Webhook received";
    }
}
