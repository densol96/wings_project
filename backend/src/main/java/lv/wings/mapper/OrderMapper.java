package lv.wings.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import lv.wings.dto.response.admin.orders.DeliveryMethodDto;
import lv.wings.dto.response.admin.orders.OrderAdminDto;
import lv.wings.dto.response.admin.orders.StatusDto;
import lv.wings.dto.response.payment.OrderSingleProductDto;
import lv.wings.dto.response.payment.OrderSummaryDto;
import lv.wings.enums.OrderStatus;
import lv.wings.model.entity.Customer;
import lv.wings.model.entity.Order;
import lv.wings.model.entity.OrderItem;

@Mapper(componentModel = "spring", uses = {CustomerMapper.class, UserMapper.class})
public interface OrderMapper {

    @Mapping(target = "productId", expression = "java(item.getProduct().getId())")
    @Mapping(target = "price", source = "item.priceAtOrderTime")
    OrderSingleProductDto orderItemToDto(OrderItem item, String name);

    @Mapping(target = "id", source = "order.id")
    @Mapping(target = "discount", source = "order.discountAtOrderTime")
    OrderSummaryDto toOrderSummaryDto(Order order, Customer customer, String deliverySumup, List<OrderSingleProductDto> items);

    @Mapping(target = "id", source = "order.id")
    OrderAdminDto toAdminDto(Order order, DeliveryMethodDto delivery);

    default StatusDto toStatus(OrderStatus status) {
        return StatusDto.builder()
                .code(status.name())
                .name(status.getLabel("lv"))
                .build();
    }
}
