package lv.wings.config.converters;

import org.springframework.stereotype.Component;

import lv.wings.enums.DeliveryMethod;

import org.springframework.core.convert.converter.Converter;

@Component
public class DeliveryMethodConverter implements Converter<String, DeliveryMethod> {
    @Override
    public DeliveryMethod convert(String source) {
        return DeliveryMethod.valueOf(source.toUpperCase());
    }
}
