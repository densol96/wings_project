package lv.wings.service.impl;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lv.wings.dto.request.payment.OrderDto;
import lv.wings.dto.response.payment.OrderItemDto;
import lv.wings.mapper.CustomerMapper;
import lv.wings.model.entity.Coupon;
import lv.wings.model.entity.Order;
import lv.wings.model.entity.OrderItem;
import lv.wings.model.entity.Product;
import lv.wings.repo.OrderItemRepository;
import lv.wings.repo.OrderRepository;
import lv.wings.service.AbstractCRUDService;
import lv.wings.service.CouponService;
import lv.wings.service.DeliveryPriceService;
import lv.wings.service.LocaleService;
import lv.wings.service.OmnivaService;
import lv.wings.service.OrderService;
import lv.wings.service.ProductService;

@Service
public class OrderServiceImpl extends AbstractCRUDService<Order, Integer> implements OrderService {

    private final LocaleService localService;
    private final DeliveryPriceService deliveryPriceService;
    private final OmnivaService omnivaService;
    private final CouponService couponService;
    private final CustomerMapper customerMapper;
    private final ProductService productService;
    private final OrderItemRepository orderItemRepository;

    public OrderServiceImpl(OrderRepository orderRepository, OrderItemRepository orderItemRepository, LocaleService localService,
            DeliveryPriceService deliveryPriceService,
            OmnivaService omnivaService, CouponService couponService, CustomerMapper customerMapper, ProductService productService) {
        super(orderRepository, "Order", "entity.order");
        this.localService = localService;
        this.deliveryPriceService = deliveryPriceService;
        this.omnivaService = omnivaService;
        this.couponService = couponService;
        this.customerMapper = customerMapper;
        this.productService = productService;
        this.orderItemRepository = orderItemRepository;
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
    public void saveNewOrder(OrderDto orderInfo, String paymentIndentId, BigDecimal discountAtOrderTime, BigDecimal total) {
        // Cascade set up the way so this works
        Order order = Order.builder()
                .paymentIntentId(paymentIndentId)
                .deliveryVariation(deliveryPriceService.findById(orderInfo.getDeliveryMethodVariationId()))
                .terminal(omnivaService.findById(orderInfo.getTerminalId()))
                .customer(customerMapper.dtoToEntity(orderInfo.getCustomerInfo()))
                .appliedCoupon(couponService.findByCode(orderInfo.getCouponCode()))
                .discountAtOrderTime(discountAtOrderTime)
                .total(total)
                .additionalDetails(orderInfo.getAdditionDetails())
                .locale(localService.getCurrentLocaleCode())
                .build();
        create(order);

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
    }

}
