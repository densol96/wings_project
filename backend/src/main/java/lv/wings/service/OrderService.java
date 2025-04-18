package lv.wings.service;

import java.math.BigDecimal;
import lv.wings.dto.request.payment.OrderDto;
import lv.wings.dto.response.payment.OrderSummaryDto;
import lv.wings.enums.OrderStatus;
import lv.wings.model.entity.Order;

public interface OrderService extends CRUDService<Order, Integer> {
    void saveNewOrder(OrderDto orderInfo, String paymentIndentId, BigDecimal discountAtOrderTime, BigDecimal total);

    void handleOrderTimeout(String paymentIntentId);

    void updateOrderStatusOnWebhookEvent(String paymentIntentId, OrderStatus newStatus);

    OrderSummaryDto getOrderSummary(String paymentIntentId);
}
