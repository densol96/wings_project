package lv.wings.service;

import java.math.BigDecimal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import lv.wings.dto.request.payment.OrderDto;
import lv.wings.dto.response.BasicMessageDto;
import lv.wings.dto.response.admin.orders.OrderAdminDto;
import lv.wings.dto.response.admin.orders.OrderFullAdminDto;
import lv.wings.dto.response.payment.OrderSummaryDto;
import lv.wings.enums.Country;
import lv.wings.enums.DeliveryMethod;
import lv.wings.enums.OrderStatus;
import lv.wings.model.entity.Order;

public interface OrderService extends CRUDService<Order, Integer> {
    void saveNewOrder(OrderDto orderInfo, String paymentIndentId, BigDecimal discountAtOrderTime, BigDecimal total);

    void handleOrderTimeout(String paymentIntentId);

    void updateOrderStatusOnWebhookEvent(String paymentIntentId, OrderStatus newStatus);

    OrderSummaryDto getOrderSummary(String paymentIntentId);

    Page<OrderAdminDto> searchOrders(Pageable pageable, String q, OrderStatus orderStatus, Country toCountry, DeliveryMethod deliveryMethod);

    OrderFullAdminDto getOrder(Integer id);

    BasicMessageDto upgradeOrder(Integer id);

    BasicMessageDto closeOrder(Integer id);

    void completePaidOrders();
}
