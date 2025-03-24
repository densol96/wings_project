package lv.wings.service.impl;

import org.springframework.stereotype.Service;
import lv.wings.model.entity.PurchaseElement;
import lv.wings.repo.PurchaseElementRepository;
import lv.wings.service.AbstractCRUDService;

@Service
public class PurchaseElementServiceImpl extends AbstractCRUDService<PurchaseElement, Integer> {

	public PurchaseElementServiceImpl(PurchaseElementRepository purchaseElementRepo) {
		super(purchaseElementRepo, "PurchaseElement", "entity.purchase-element");
	}

}
