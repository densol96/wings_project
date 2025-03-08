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

import lv.wings.model.Purchase;
import lv.wings.model.PurchaseElement;
import lv.wings.repo.IPurchaseElementRepo;
import lv.wings.repo.IPurchaseRepo;
import lv.wings.service.ICRUDService;

@Service
public class PurchaseServiceImpl implements ICRUDService<Purchase> {

    @Autowired
    private IPurchaseRepo purchaseRepo;

    @Autowired
    private IPurchaseElementRepo elementRepo;

    @Override
    @Cacheable("Purchases")
    public List<Purchase> retrieveAll() {
        if (purchaseRepo.count() == 0)
            throw new RuntimeException("There are no purchases");

        return purchaseRepo.findAll();
    }

    @Override
    @Cacheable(value = "Purchases", key = "#pageable.pageNumber + '-' + #pageable.pageSize + '-' + #pageable.sort")
    public Page<Purchase> retrieveAll(Integer page, Integer size, String sortBy, String sortDirection) {
        if (purchaseRepo.count() == 0)
            throw new RuntimeException("There are no purchases");
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDirection), sortBy));
        return purchaseRepo.findAll(pageable);
    }

    @Override
    @Cacheable(value = "Purchases", key = "#id")
    public Purchase retrieveById(Integer id) {
        if (id < 0)
            throw new RuntimeException("Invalid ID");

        if (purchaseRepo.existsById(id)) {
            return purchaseRepo.findById(id).get();
        } else {
            throw new RuntimeException("Purchase with id [" + id + "] does not exist");
        }
    }

    @Override
    @CacheEvict(value = "Purchases", allEntries = true)
    public void deleteById(Integer id) {
        Purchase purchaseToDelete = retrieveById(id);

        List<PurchaseElement> purchaseElements = elementRepo.findByPurchase(purchaseToDelete);

        for (Integer i = 0; i < purchaseElements.size(); i++) {
            purchaseElements.get(i).setPurchase(null);
            elementRepo.save(purchaseElements.get(i));
        }

        purchaseRepo.delete(purchaseToDelete);
    }

    @Override
    @CacheEvict(value = "Purchases", allEntries = true)
    public void create(Purchase purchase) {
        purchaseRepo.save(purchase);
    }

    @Override
    @CacheEvict(value = "Purchases", allEntries = true)
    @CachePut(value = "Purchases", key = "#id")
    public void update(Integer id, Purchase purchase) {

    }

}
