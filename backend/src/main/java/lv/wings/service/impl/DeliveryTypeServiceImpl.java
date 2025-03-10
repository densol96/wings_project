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

import lv.wings.model.entity.DeliveryType;
import lv.wings.model.entity.Purchase;
import lv.wings.repo.IDeliveryTypeRepo;
import lv.wings.repo.IPurchaseRepo;
import lv.wings.service.ICRUDService;

@Service
public class DeliveryTypeServiceImpl implements ICRUDService<DeliveryType> {

    @Autowired
    private IDeliveryTypeRepo deliveryTypeRepo;

    @Autowired
    private IPurchaseRepo purchaseRepo;

    @Override
    @Cacheable("DeliveryTypes")
    public List<DeliveryType> retrieveAll() {
        if (deliveryTypeRepo.count() == 0)
            throw new RuntimeException("There are no delivery types");

        return deliveryTypeRepo.findAll();
    }

    @Override
    @Cacheable(value = "DeliveryTypes", key = "#pageable.pageNumber + '-' + #pageable.pageSize + '-' + #pageable.sort")
    public Page<DeliveryType> retrieveAll(Integer page, Integer size, String sortBy, String sortDirection) {
        if (deliveryTypeRepo.count() == 0)
            throw new RuntimeException("There are no delivery types");
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDirection), sortBy));
        return deliveryTypeRepo.findAll(pageable);
    }

    @Override
    @Cacheable(value = "DeliveryTypes", key = "#id")
    public DeliveryType retrieveById(Integer id) {
        if (id < 0)
            throw new RuntimeException("Invalid ID!");

        if (deliveryTypeRepo.existsById(id)) {
            return deliveryTypeRepo.findById(id).get();
        } else {
            throw new RuntimeException("DElivery type with ID [" + id + "] does not exist");
        }
    }

    @Override
    @CacheEvict(value = "DeliveryTypes", allEntries = true)
    public void deleteById(Integer id) {
        DeliveryType deliveryTypeToDelete = retrieveById(id);

        List<Purchase> purchases = purchaseRepo.findByDeliveryType(deliveryTypeToDelete);

        for (Integer i = 0; i < purchases.size(); i++) {
            purchases.get(i).setDeliveryType(null);
            purchaseRepo.save(purchases.get(i));
        }

        deliveryTypeRepo.delete(deliveryTypeToDelete);
    }

    @Override
    @CacheEvict(value = "DeliveryTypes", allEntries = true)
    public void create(DeliveryType deliveryType) {
        DeliveryType deliveryTypeExist = deliveryTypeRepo.findByTitle(deliveryType.getTitle());

        if (deliveryTypeExist == null) {
            deliveryTypeRepo.save(deliveryType);
        } else {
            throw new RuntimeException("Delivery type already exists");
        }
    }

    @Override
    @CacheEvict(value = "DeliveryTypes", allEntries = true)
    @CachePut(value = "DeliveryTypes", key = "#id")
    public void update(Integer id, DeliveryType deliveryType) {
        DeliveryType deliveryTypeToUpdate = retrieveById(id);

        deliveryTypeToUpdate.setTitle(deliveryType.getTitle());
        deliveryTypeToUpdate.setDescription(deliveryType.getDescription());

        deliveryTypeRepo.save(deliveryTypeToUpdate);

    }

}
