package lv.wings.service;

import java.util.List;
import lv.wings.dto.response.delivery.DeliveryDto;
import lv.wings.enums.Country;
import lv.wings.enums.LocaleCode;
import lv.wings.model.entity.DeliveryType;
import lv.wings.model.entity.Order;
import lv.wings.model.translation.DeliveryTypeTranslation;

public interface DeliveryTypeService {
    List<DeliveryDto> getDeliveryMethodsPerCountry(Country country);

    DeliveryTypeTranslation getRightTranslation(DeliveryType product);

    String proccessDeliveryMethod(Order order, LocaleCode localeCode);
}
