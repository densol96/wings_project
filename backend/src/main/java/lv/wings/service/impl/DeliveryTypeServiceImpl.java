package lv.wings.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import lv.wings.dto.response.delivery.DeliveryDto;
import lv.wings.enums.Country;
import lv.wings.mapper.DeliveryTypeMapper;
import lv.wings.model.entity.DeliveryPrice;
import lv.wings.model.entity.DeliveryType;
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
}
