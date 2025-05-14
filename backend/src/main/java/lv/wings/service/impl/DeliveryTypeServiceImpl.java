package lv.wings.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import lv.wings.dto.response.delivery.DeliveryDto;
import lv.wings.enums.Country;
import lv.wings.enums.DeliveryMethod;
import lv.wings.enums.LocaleCode;
import lv.wings.mapper.DeliveryTypeMapper;
import lv.wings.model.entity.DeliveryPrice;
import lv.wings.model.entity.DeliveryType;
import lv.wings.model.entity.Order;
import lv.wings.model.entity.Terminal;
import lv.wings.model.translation.DeliveryTypeTranslation;
import lv.wings.repo.DeliveryPriceRepository;
import lv.wings.repo.DeliveryTypeRepository;
import lv.wings.service.AbstractTranslatableCRUDService;
import lv.wings.service.DeliveryTypeService;
import lv.wings.service.LocaleService;

@Service
public class DeliveryTypeServiceImpl extends AbstractTranslatableCRUDService<DeliveryType, DeliveryTypeTranslation, Integer> implements DeliveryTypeService {

    private final DeliveryPriceRepository deliveryPriceRepository;
    private final DeliveryTypeMapper deliveryTypeMapper;

    public DeliveryTypeServiceImpl(DeliveryTypeRepository deliveryTypeRepo, DeliveryPriceRepository deliveryPriceRepository,
            DeliveryTypeMapper deliveryTypeMapper, LocaleService localeService) {
        super(deliveryTypeRepo, "DeliveryType", "entity.delivery-type", localeService);
        this.deliveryPriceRepository = deliveryPriceRepository;
        this.deliveryTypeMapper = deliveryTypeMapper;
    }

    @Override
    public List<DeliveryDto> getDeliveryMethodsPerCountry(Country country) {
        return deliveryPriceRepository.findAllByCountry(country)
                .stream()
                .map((DeliveryPrice price) -> {
                    DeliveryType type = price.getDeliveryType();
                    return deliveryTypeMapper.toDeliveryDto(
                            type,
                            price,
                            getRightTranslation(type, DeliveryTypeTranslation.class));
                })
                .toList();
    }

    @Override
    public DeliveryTypeTranslation getRightTranslation(DeliveryType deliveryType) {
        return getRightTranslation(deliveryType, DeliveryTypeTranslation.class);
    }

    @Override
    public DeliveryTypeTranslation getRightTranslationForSelectedLocale(DeliveryType deliveryType, LocaleCode localCode) {
        return getRightTranslationForSelectedLocale(deliveryType, DeliveryTypeTranslation.class, localCode);
    }

    @Override
    public String proccessDeliveryMethod(Order order, LocaleCode localCode) {
        DeliveryPrice deliveryPrice = order.getDeliveryVariation();
        DeliveryType deliveryType = deliveryPrice.getDeliveryType();
        String name = order.getDeliveryPriceAtOrderTime().toPlainString() + " € - "
                + getRightTranslationForSelectedLocale(deliveryPrice.getDeliveryType(), localCode).getTitle();
        if (deliveryType.getMethod() == DeliveryMethod.PARCEL_MACHINE) {
            Terminal omniva = order.getTerminal();
            name += " - " + omniva.getName() + " - " + omniva.getAddress();
        }
        return name;
    }
}
