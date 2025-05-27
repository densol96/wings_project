package lv.wings.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import lv.wings.dto.request.admin.orders.UpgradeOrderDto;
import lv.wings.dto.request.payment.OrderDto;
import lv.wings.dto.response.BasicMessageDto;
import lv.wings.dto.response.admin.orders.CouponAdminDto;
import lv.wings.dto.response.admin.orders.CustomerFullAdminDto;
import lv.wings.dto.response.admin.orders.DeliveryMethodDto;
import lv.wings.dto.response.admin.orders.FullDeliveryInfoDto;
import lv.wings.dto.response.admin.orders.OrderAdminDto;
import lv.wings.dto.response.admin.orders.OrderFullAdminDto;
import lv.wings.dto.response.admin.orders.StatusDto;
import lv.wings.dto.response.payment.OrderSingleProductDto;
import lv.wings.dto.response.payment.OrderSummaryDto;
import lv.wings.enums.Country;
import lv.wings.enums.DeliveryMethod;
import lv.wings.enums.LocaleCode;
import lv.wings.enums.OrderStatus;
import lv.wings.enums.TranslationMethod;
import lv.wings.exception.entity.EntityNotFoundException;
import lv.wings.exception.other.ConflictException;
import lv.wings.exception.payment.WebhookException;
import lv.wings.mapper.CustomerMapper;
import lv.wings.mapper.DeliveryTypeMapper;
import lv.wings.mapper.OrderMapper;
import lv.wings.mapper.UserMapper;
import lv.wings.model.entity.Address;
import lv.wings.model.entity.DeliveryPrice;
import lv.wings.model.entity.DeliveryType;
import lv.wings.model.entity.Order;
import lv.wings.model.entity.OrderItem;
import lv.wings.model.entity.Product;
import lv.wings.model.entity.Terminal;
import lv.wings.model.translation.DeliveryTypeTranslation;
import lv.wings.repo.OrderItemRepository;
import lv.wings.repo.OrderRepository;
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
import lv.wings.service.TranslationService;

@Service
@Slf4j
public class OrderServiceImpl extends AbstractCRUDService<Order, Integer> implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderMapper orderMapper;
    private final UserMapper userMapper;

    private final LocaleService localeService;
    private final DeliveryPriceService deliveryPriceService;
    private final DeliveryTypeService deliveryTypeService;
    private final OmnivaService omnivaService;
    private final CouponService couponService;
    private final ProductService productService;
    private final EmailSenderService emailSenderService;
    private final TranslationService translationService;

    private final FrontendCacheInvalidator nextJsInvalidator;


    private final DeliveryTypeMapper deliveryTypeMapper;
    private final CustomerMapper customerMapper;


    public static final int CONSIDER_CANCELLED_AFTER_MINS = 1;
    public static final int DAYS_AFTER_SHIPPING = 3;


    public OrderServiceImpl(
            OrderRepository orderRepository,
            OrderItemRepository orderItemRepository,
            LocaleService localeService,
            DeliveryPriceService deliveryPriceService,
            DeliveryTypeService deliveryTypeService,
            OrderMapper orderMapper,
            OmnivaService omnivaService,
            CouponService couponService,
            CustomerMapper customerMapper,
            ProductService productService,
            FrontendCacheInvalidator nextJsInvalidator,
            EmailSenderService emailSenderService,
            DeliveryTypeMapper deliveryTypeMapper,
            TranslationService translationService,
            UserMapper userMapper) {
        super(orderRepository, "Order", "entity.order");
        this.orderRepository = orderRepository;
        this.localeService = localeService;
        this.deliveryPriceService = deliveryPriceService;
        this.deliveryTypeService = deliveryTypeService;
        this.omnivaService = omnivaService;
        this.couponService = couponService;
        this.customerMapper = customerMapper;
        this.productService = productService;
        this.orderItemRepository = orderItemRepository;
        this.nextJsInvalidator = nextJsInvalidator;
        this.emailSenderService = emailSenderService;
        this.orderMapper = orderMapper;
        this.deliveryTypeMapper = deliveryTypeMapper;
        this.translationService = translationService;
        this.userMapper = userMapper;
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
                .locale(localeService.getCurrentLocaleCode())
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
            System.out.println("RESERVATION CANCELLED!");
            order.getOrderItems().forEach(orderItem -> {
                productService.getProductByIdWithLock(orderItem.getProduct().getId()).ifPresent((product -> {
                    product.setAmount(product.getAmount() + orderItem.getAmount());
                    productService.persist(product);
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
            return;
        }

        // means, order will be proccessed in the webhook
        if (paymentIntent.getStatus().equals("succeeded") || paymentIntent.getStatus().equals("canceled")
                || paymentIntent.getStatus().equals("payment_failed")) {
            return;
        }

        try {
            paymentIntent.cancel();
            /*
             * After cancelling, Stripe itself will trigger the Cancel Event!
             * So, No need to call updateOrderStatusOnWebhookEvent(paymentIntentId, OrderStatus.CANCELLED);
             * This is causing bugs with reservation system!
             */
            // updateOrderStatusOnWebhookEvent(paymentIntentId, OrderStatus.CANCELLED);
        } catch (StripeException exception) {
            updateOrderStatusOnWebhookEvent(paymentIntentId, OrderStatus.CANCELLED);
            // throw new RuntimeException("THIS SHOULD ROLLBACK THE TRANSACTION IN CASE OF THE EXCEPTION");
        }
    }

    @Override
    public OrderSummaryDto getOrderSummary(String paymentIntentId) {
        Order order = orderRepository.findByPaymentIntentIdWithItems(paymentIntentId)
                .orElseThrow(() -> new EntityNotFoundException(entityNameKey, entityName, paymentIntentId));
        List<OrderSingleProductDto> items =
                order.getOrderItems().stream()
                        .map((i) -> orderMapper.orderItemToDto(i, productService.getRightTranslation(i.getProduct()).getTitle())).toList();
        return orderMapper.toOrderSummaryDto(order, order.getCustomer(),
                deliveryTypeService.proccessDeliveryMethod(order, localeService.getCurrentLocaleCode()),
                items);
    }

    @Override
    public Page<OrderAdminDto> searchOrders(Pageable pageable, String q, OrderStatus orderStatus, Country toCountry, DeliveryMethod deliveryMethod) {
        return orderRepository.findFilteredOrders(pageable, orderStatus, toCountry, deliveryMethod, q).map(this::mapOrderToDto);
    }

    @Override
    public OrderFullAdminDto getOrder(Integer id) {
        Order order = findById(id);
        StatusDto status = orderMapper.toStatus(order.getStatus());
        FullDeliveryInfoDto deliveryInfo = toAdminDeliveryInfo(order);
        CustomerFullAdminDto customer = customerMapper.toFullAdminDto(order.getCustomer());
        CouponAdminDto couponDto = couponService.orderToCouponAdminDto(order);
        List<OrderSingleProductDto> items =
                order.getOrderItems().stream()
                        .map((i) -> orderMapper.orderItemToDto(i, productService.getSelectedTranslation(i.getProduct(), LocaleCode.LV).getTitle())).toList();
        return OrderFullAdminDto.builder()
                .id(order.getId())
                .status(status)
                .deliveryInfo(deliveryInfo)
                .customerInfo(customer)
                .couponInfo(couponDto)
                .total(order.getTotal())
                .locale(order.getLocale())
                .items(items)
                .createdAt(order.getCreatedAt())
                .lastModifiedAt(order.getLastModifiedAt())
                .lastModifiedBy(userMapper.toMinUserDto(order.getLastModifiedBy()))
                .build();
    }

    private OrderAdminDto mapOrderToDto(Order order) {
        DeliveryType deliveryType = order.getDeliveryVariation().getDeliveryType();
        DeliveryTypeTranslation translation = deliveryTypeService.getRightTranslationForSelectedLocale(deliveryType, LocaleCode.LV);
        DeliveryMethodDto deliveryDto = DeliveryMethodDto.builder()
                .methodName(translation.getTitle())
                .methodCode(deliveryType.getMethod()).build();

        String fullDeliveryAddress = null;

        if (deliveryType.getMethod() == DeliveryMethod.COURIER) {
            Address customer = order.getCustomer().getAddress();
            fullDeliveryAddress = customer.getFullAddress();
        } else if (deliveryType.getMethod() == DeliveryMethod.PARCEL_MACHINE) {
            Terminal terminal = order.getTerminal();
            fullDeliveryAddress = terminal.getName() + " - " + terminal.getAddress();
        }
        deliveryDto.setFullNameAddress(fullDeliveryAddress);
        return orderMapper.toAdminDto(order, deliveryDto);
    }

    private FullDeliveryInfoDto toAdminDeliveryInfo(Order order) {
        DeliveryPrice deliveryVariation = order.getDeliveryVariation();
        String deliveryMethodName = deliveryTypeService.getRightTranslationForSelectedLocale(deliveryVariation.getDeliveryType(), LocaleCode.LV).getTitle();
        return deliveryTypeMapper.toAdminDeliveryInfo(deliveryVariation, order.getDeliveryPriceAtOrderTime(), deliveryMethodName);
    }

    @Override
    public BasicMessageDto upgradeOrder(Integer id, UpgradeOrderDto dto) {
        Order order = findById(id);
        if (order.getStatus() != OrderStatus.PAID || order.getDeliveryVariation().getDeliveryType().getMethod() == DeliveryMethod.PICKUP)
            throw new ConflictException("Pasūtījumu var nosūtīt tikai tad, ja tas ir apmaksāts un piegādes metode nav 'Saņemšana veikalā' (Pickup).");

        String additionalComment = dto.getAdditionalComment();
        String localisedComment = null;

        if (additionalComment != null && !additionalComment.isBlank()) {
            if (order.getLocale() != LocaleCode.LV) {
                if (dto.getTranslateMethod() == TranslationMethod.MANUAL) {
                    localisedComment = additionalComment;
                } else {
                    localisedComment = translationService.translateToEnglish(additionalComment);
                }
            } else {
                localisedComment = additionalComment;
            }
        }
        order.setStatus(OrderStatus.SHIPPED);
        orderRepository.save(order);
        emailSenderService.sendOrderWasShippedEmail(order, localisedComment);
        return new BasicMessageDto("Pasūtījuma statuss ir mainīts, un klients ir informēts, ka pasūtījums ir nosūtīts.");
    }

    @Override
    public BasicMessageDto closeOrder(Integer id) {
        Order order = findById(id);

        LocalDateTime lastModifiedAt = order.getLastModifiedAt();

        boolean isShippedAndCanBeClosed =
                order.getStatus() == OrderStatus.SHIPPED
                        && (lastModifiedAt == null || lastModifiedAt.isBefore(LocalDateTime.now().minusDays(DAYS_AFTER_SHIPPING)));
        boolean orderIsPaidInStore =
                order.getStatus() == OrderStatus.PAID && order.getDeliveryVariation().getDeliveryType().getMethod() == DeliveryMethod.PICKUP;

        if (isShippedAndCanBeClosed || orderIsPaidInStore) {
            order.setStatus(OrderStatus.COMPLETED);
            orderRepository.save(order);
            emailSenderService.sendOrderClosedEmail(order);
        } else {
            if (order.getStatus() == OrderStatus.SHIPPED) {
                throw new ConflictException("Pasūtījumu nevar pabeigt, jo tas tika nosūtīts pārāk nesen.");
            } else if (order.getStatus() == OrderStatus.PAID) {
                throw new ConflictException("Pasūtījumu var pabeigt tikai, ja tas ir paredzēts saņemšanai uz vietas.");
            } else {
                throw new ConflictException("Pasūtījumu var pabeigt tikai, ja tas ir apmaksāts un nosūtīts vai apmaksāts uz vietas.");
            }
        }
        return new BasicMessageDto("Pasūtījuma statuss ir mainīts, un klients ir informēts, ka pasūtījums ir nosūtīts.");
    }

    @Override
    @Scheduled(cron = "0 0 4 * * *") // 4am everyday
    public void completePaidOrders() {
        List<Order> paidOrders = orderRepository.findOrdersThatCanBeCompleted(LocalDateTime.now().minusDays(DAYS_AFTER_SHIPPING));
        for (Order order : paidOrders) {
            order.setStatus(OrderStatus.COMPLETED);
            emailSenderService.sendOrderClosedEmail(order);
        }
        orderRepository.saveAll(paidOrders);
        log.info("*** {} paid orders have been automatically upgraded by the system!");
    }
}
