package lv.wings.service.impl;

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

import lv.wings.model.entity.Purchase;
import lv.wings.model.entity.PurchaseElement;
import lv.wings.repo.PurchaseElementRepository;
import lv.wings.repo.PurchaseRepository;
import lv.wings.service.AbstractCRUDService;
import lv.wings.service.ICRUDService;

@Service
public class PurchaseServiceImpl extends AbstractCRUDService<Purchase, Integer> {

    public PurchaseServiceImpl(PurchaseRepository purchaseRepo) {
        super(purchaseRepo, "Purchase", "entity.purchase");
    }

}
