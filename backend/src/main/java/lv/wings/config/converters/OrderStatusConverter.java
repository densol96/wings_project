package lv.wings.config.converters;

import org.springframework.stereotype.Component;
import org.springframework.core.convert.converter.Converter;

import lv.wings.enums.OrderStatus;

@Component
public class OrderStatusConverter implements Converter<String, OrderStatus> {
    @Override
    public OrderStatus convert(String source) {
        return OrderStatus.valueOf(source.toUpperCase());
    }
}
