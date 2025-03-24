package lv.wings.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import lv.wings.model.entity.Event;
import lv.wings.model.entity.PaymentType;
import lv.wings.model.entity.Purchase;
import lv.wings.model.translation.EventTranslation;
import lv.wings.model.translation.PaymentTypeTranslation;
import lv.wings.repo.PaymentTypeRepository;
import lv.wings.repo.PurchaseRepository;
import lv.wings.service.AbstractTranslatableCRUDService;
import lv.wings.service.ICRUDService;
import lv.wings.service.LocaleService;

@Service
public class PaymentTypeServiceImpl extends AbstractTranslatableCRUDService<PaymentType, PaymentTypeTranslation, Integer> {

    public PaymentTypeServiceImpl(PaymentTypeRepository paymentTypeRepo, LocaleService localeService) {
        super(paymentTypeRepo, "PaymentType", "entity.payment-type", localeService);
    }
}
