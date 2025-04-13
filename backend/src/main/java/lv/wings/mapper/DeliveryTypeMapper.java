package lv.wings.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import lv.wings.dto.response.delivery.DeliveryDto;
import lv.wings.model.entity.DeliveryPrice;
import lv.wings.model.entity.DeliveryType;
import lv.wings.model.translation.DeliveryTypeTranslation;

@Mapper(componentModel = "spring")
public interface DeliveryTypeMapper {

    @Mapping(target = "id", source = "variation.id")
    DeliveryDto toDeliveryDto(DeliveryType type, DeliveryPrice variation, DeliveryTypeTranslation dtTranslation);
}
