package lv.wings.service.impl;

import org.springframework.stereotype.Service;

import lv.wings.model.entity.DeliveryType;
import lv.wings.model.translation.DeliveryTypeTranslation;
import lv.wings.repo.DeliveryTypeRepository;
import lv.wings.service.AbstractTranslatableCRUDService;
import lv.wings.service.LocaleService;

@Service
public class DeliveryTypeServiceImpl extends AbstractTranslatableCRUDService<DeliveryType, DeliveryTypeTranslation, Integer> {

    public DeliveryTypeServiceImpl(DeliveryTypeRepository deliveryTypeRepo, LocaleService localeService) {
        super(deliveryTypeRepo, "DeliveryType", "entity.delivery-type", localeService);
    }
}
