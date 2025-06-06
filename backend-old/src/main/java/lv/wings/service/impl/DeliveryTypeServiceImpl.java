package lv.wings.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lv.wings.model.DeliveryType;
import lv.wings.model.Purchase;
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
    public ArrayList<DeliveryType> retrieveAll() throws Exception {
        if(deliveryTypeRepo.count() == 0) throw new Exception("There are no delivery types");

        return (ArrayList<DeliveryType>) deliveryTypeRepo.findAll();
    }

    @Override
    @Cacheable(value = "DeliveryTypes", key = "#pageable.pageNumber + '-' + #pageable.pageSize + '-' + #pageable.sort")
    public Page<DeliveryType> retrieveAll(Pageable pageable) throws Exception {
        if(deliveryTypeRepo.count() == 0) throw new Exception("There are no delivery types");
        return (Page<DeliveryType>) deliveryTypeRepo.findAll(pageable);
    }

    @Override
    @Cacheable(value="DeliveryTypes", key="#id")
    public DeliveryType retrieveById(int id) throws Exception {
        if(id < 0) throw new Exception("Invalid ID!");

        if(deliveryTypeRepo.existsById(id)){
            return deliveryTypeRepo.findById(id).get();
        } else {
            throw new Exception("DElivery type with ID [" + id+ "] does not exist");
        }
    }

    @Override
    @CacheEvict(value = "DeliveryTypes", allEntries = true)
    public void deleteById(int id) throws Exception {
        DeliveryType deliveryTypeToDelete = retrieveById(id);

        ArrayList<Purchase> purchases = purchaseRepo.findByDeliveryType(deliveryTypeToDelete);
        
        for(int i = 0; i < purchases.size(); i++) {
            purchases.get(i).setDeliveryType(null);
            purchaseRepo.save(purchases.get(i));
        }

        deliveryTypeRepo.delete(deliveryTypeToDelete);
    }

    @Override
    @CacheEvict(value = "DeliveryTypes", allEntries = true)
    public void create(DeliveryType deliveryType) throws Exception {
        DeliveryType deliveryTypeExist = deliveryTypeRepo.findByTitle(deliveryType.getTitle());

        if(deliveryTypeExist == null) {
            deliveryTypeRepo.save(deliveryType);
        } else {
            throw new Exception("Delivery type already exists");
        }
    }

    @Override
    @CacheEvict(value = "DeliveryTypes", allEntries = true)
    @CachePut(value="DeliveryTypes", key="#id")
    public void update(int id, DeliveryType deliveryType) throws Exception {
        DeliveryType deliveryTypeToUpdate = retrieveById(id);

        deliveryTypeToUpdate.setTitle(deliveryType.getTitle());
        deliveryTypeToUpdate.setDescription(deliveryType.getDescription());

        deliveryTypeRepo.save(deliveryTypeToUpdate);
        
    }
    


}
