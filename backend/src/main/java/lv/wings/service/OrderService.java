package lv.wings.service;

import java.math.BigDecimal;
import lv.wings.dto.request.payment.OrderDto;
import lv.wings.model.entity.Order;

public interface OrderService extends CRUDService<Order, Integer> {
    void saveNewOrder(OrderDto orderInfo, String paymentIndentId, BigDecimal discountAtOrderTime, BigDecimal total);
}
