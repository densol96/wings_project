package lv.wings.service;

import java.util.List;
import lv.wings.dto.response.delivery.DeliveryDto;
import lv.wings.enums.Country;

public interface DeliveryTypeService {
    List<DeliveryDto> getDeliveryMethodsPerCountry(Country country);
}
