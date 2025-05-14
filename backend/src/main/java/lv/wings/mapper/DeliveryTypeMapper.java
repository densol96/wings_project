package lv.wings.mapper;

import java.math.BigDecimal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import lv.wings.dto.response.admin.orders.FullDeliveryInfoDto;
import lv.wings.dto.response.delivery.DeliveryDto;
import lv.wings.model.entity.DeliveryPrice;
import lv.wings.model.entity.DeliveryType;
import lv.wings.model.translation.DeliveryTypeTranslation;

@Mapper(componentModel = "spring")
public interface DeliveryTypeMapper {

    @Mapping(target = "id", source = "variation.id")
    DeliveryDto toDeliveryDto(DeliveryType type, DeliveryPrice variation, DeliveryTypeTranslation dtTranslation);

    @Mapping(target = "methodCode", expression = "java(cariation.getDeliveryType().getMethod())")
    FullDeliveryInfoDto toAdminDeliveryInfo(DeliveryPrice variation, BigDecimal deliveryPriceAtOrderTime, String methodName);
}
