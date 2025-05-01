package lv.wings.service.impl;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.stereotype.Service;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lv.wings.dto.request.payment.OrderDto;
import lv.wings.dto.response.payment.OrderSingleProductDto;
import lv.wings.dto.response.payment.OrderSummaryDto;
import lv.wings.enums.OrderStatus;
import lv.wings.exception.entity.EntityNotFoundException;
import lv.wings.exception.payment.WebhookException;
import lv.wings.mapper.CustomerMapper;
import lv.wings.mapper.OrderMapper;
import lv.wings.model.entity.Customer;
import lv.wings.model.entity.DeliveryPrice;
import lv.wings.model.entity.DeliveryType;
import lv.wings.model.entity.Order;
import lv.wings.model.entity.OrderItem;
import lv.wings.model.entity.Product;
import lv.wings.repo.OrderItemRepository;
import lv.wings.repo.OrderRepository;
import lv.wings.repo.ProductRepository;
import lv.wings.service.AbstractCRUDService;
import lv.wings.service.CouponService;
import lv.wings.service.DeliveryPriceService;
import lv.wings.service.DeliveryTypeService;
import lv.wings.service.EmailSenderService;
import lv.wings.service.FrontendCacheInvalidator;
import lv.wings.service.LocaleService;
import lv.wings.service.OmnivaService;
import lv.wings.service.OrderService;
import lv.wings.service.ProductService;

@Service
public class OrderServiceImpl extends AbstractCRUDService<Order, Integer> implements OrderService {

    private final OrderRepository orderRepository;
    private final LocaleService localService;
    private final DeliveryPriceService deliveryPriceService;
    private final DeliveryTypeService deliveryTypeService;
    private final OmnivaService omnivaService;
    private final CouponService couponService;
    private final CustomerMapper customerMapper;
    private final ProductService productService;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;
    private final FrontendCacheInvalidator nextJsInvalidator;
    private final EmailSenderService emailSenderService;
    private final OrderMapper orderMapper;


    public static final int CONSIDER_CANCELLED_AFTER_MINS = 1;


    public OrderServiceImpl(OrderRepository orderRepository, OrderItemRepository orderItemRepository, LocaleService localService,
            DeliveryPriceService deliveryPriceService, DeliveryTypeService deliveryTypeService, OrderMapper orderMapper,
            OmnivaService omnivaService, CouponService couponService, CustomerMapper customerMapper, ProductService productService,
            ProductRepository productRepository, FrontendCacheInvalidator nextJsInvalidator, EmailSenderService emailSenderService) {
        super(orderRepository, "Order", "entity.order");
        this.orderRepository = orderRepository;
        this.localService = localService;
        this.deliveryPriceService = deliveryPriceService;
        this.deliveryTypeService = deliveryTypeService;
        this.omnivaService = omnivaService;
        this.couponService = couponService;
        this.customerMapper = customerMapper;
        this.productService = productService;
        this.orderItemRepository = orderItemRepository;
        this.productRepository = productRepository;
        this.nextJsInvalidator = nextJsInvalidator;
        this.emailSenderService = emailSenderService;
        this.orderMapper = orderMapper;
    }

    /**
     * This method is used by PaymentService which does all the required validations before saving data to a DB. And it is also wrapped into a
     * transaction.
     * => Both methods are operating within the single transaction and operate the same state of data.
     * 
     * Therefore, I am not doing the same validation checks here again. Simply take data and save.
     *
     * + i can safely call .get on Optionals / not check for null
     * 
     * + no race conditions, I might need to do a DB query Again to create a relationship and entity.s data will be the same as during validation in
     * PaymentService
     */
    @Override
    @Transactional
    public void saveNewOrder(@NonNull OrderDto orderInfo, @NonNull String paymentIndentId, @NonNull BigDecimal discountAtOrderTime, @NonNull BigDecimal total) {
        // Cascade set up the way so this works
        Order order = Order.builder()
                .paymentIntentId(paymentIndentId)
                .deliveryVariation(deliveryPriceService.findById(orderInfo.getDeliveryMethodVariationId()))
                .terminal(orderInfo.getTerminalId() != null ? omnivaService.findById(orderInfo.getTerminalId()) : null)
                .customer(customerMapper.dtoToEntity(orderInfo.getCustomerInfo()))
                .appliedCoupon(couponService.findByCode(orderInfo.getCouponCode()))
                .discountAtOrderTime(discountAtOrderTime)
                .total(total)
                .locale(localService.getCurrentLocaleCode())
                .build();
        this.persist(order);

        List<OrderItem> orderItems = orderInfo.getOrderItems().stream().map(dto -> {
            Product product = productService.findById(dto.getProductId());
            return OrderItem.builder()
                    .order(order)
                    .product(product)
                    .amount(dto.getAmount())
                    .priceAtOrderTime(product.getPrice())
                    .build();
        }).toList();

        orderItemRepository.saveAll(orderItems);
        nextJsInvalidator.invalidateProducts();
    }

    @Transactional
    @Override
    public void updateOrderStatusOnWebhookEvent(String paymentIntentId, OrderStatus newStatus) {
        Order order = orderRepository.findByPaymentIntentIdWithItems(paymentIntentId)
                .orElseThrow(() -> new WebhookException("Order not found for PaymentIntent ID: " + paymentIntentId));

        // Amounts have been reserved during paymentIntent creation => if not PAID, we need to release them
        if (newStatus != OrderStatus.PAID) {
            order.getOrderItems().forEach(orderItem -> {
                productService.getProductByIdWithLock(orderItem.getProduct().getId()).ifPresent((product -> {
                    product.setAmount(product.getAmount() + orderItem.getAmount());
                    productRepository.save(product);
                }));
            });
            nextJsInvalidator.invalidateProducts();
        } else {
            emailSenderService.sendOrderSuccessEmail(order);
        }

        order.setStatus(newStatus);
        orderRepository.save(order);
    }

    @Override
    public void handleOrderTimeout(String paymentIntentId) {
        PaymentIntent paymentIntent = null;
        try {
            paymentIntent = PaymentIntent.retrieve(paymentIntentId);
        } catch (Exception e) {
            updateOrderStatusOnWebhookEvent(paymentIntentId, OrderStatus.CANCELLED);
        }

        if (paymentIntent == null)
            return;

        // means, order will be proccessed in the webhook
        if (paymentIntent.getStatus().equals("succeeded") || paymentIntent.getStatus().equals("canceled")
                || paymentIntent.getStatus().equals("payment_failed")) {
            return;
        }

        try {
            paymentIntent.cancel();
            updateOrderStatusOnWebhookEvent(paymentIntentId, OrderStatus.CANCELLED);
        } catch (StripeException exception) {
            throw new RuntimeException("THIS SHOULD ROLLBACK THE TRANSACTION IN CASE OF THE EXCEPTION");
        }
    }

    @Override
    public OrderSummaryDto getOrderSummary(String paymentIntentId) {
        Order order = orderRepository.findByPaymentIntentIdWithItems(paymentIntentId)
                .orElseThrow(() -> new EntityNotFoundException(entityNameKey, entityName, paymentIntentId));
        List<OrderSingleProductDto> items =
                order.getOrderItems().stream()
                        .map((i) -> orderMapper.orderItemToDto(i, productService.getRightTranslation(i.getProduct()).getTitle())).toList();
        return orderMapper.toOrderSummaryDto(order, order.getCustomer(), deliveryTypeService.proccessDeliveryMethod(order, localService.getCurrentLocaleCode()),
                items);
    }

}
