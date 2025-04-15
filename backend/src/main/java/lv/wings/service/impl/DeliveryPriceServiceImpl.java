package lv.wings.service.impl;

import org.springframework.stereotype.Service;
import lv.wings.model.entity.DeliveryPrice;
import lv.wings.repo.DeliveryPriceRepository;
import lv.wings.service.AbstractCRUDService;
import lv.wings.service.DeliveryPriceService;

@Service
public class DeliveryPriceServiceImpl extends AbstractCRUDService<DeliveryPrice, Integer> implements DeliveryPriceService {
    public DeliveryPriceServiceImpl(DeliveryPriceRepository dpRepo) {
        super(dpRepo, "Delivery Price", "entity.delivery-price");
    }
}
