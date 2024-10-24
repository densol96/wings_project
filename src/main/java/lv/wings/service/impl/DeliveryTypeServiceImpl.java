package lv.wings.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
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
    public ArrayList<DeliveryType> retrieveAll() throws Exception {
        if(deliveryTypeRepo.count() == 0) throw new Exception("There are no delivery types");

        return (ArrayList<DeliveryType>) deliveryTypeRepo.findAll();
    }

    @Override
    public DeliveryType retrieveById(int id) throws Exception {
        if(id < 0) throw new Exception("Invalid ID!");

        if(deliveryTypeRepo.existsById(id)){
            return deliveryTypeRepo.findById(id).get();
        } else {
            throw new Exception("DElivery type with ID [" + id+ "] does not exist");
        }
    }

    @Override
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
    public void create(DeliveryType deliveryType) throws Exception {
        DeliveryType deliveryTypeExist = deliveryTypeRepo.findByTitle(deliveryType.getTitle());

        if(deliveryTypeExist == null) {
            deliveryTypeRepo.save(deliveryType);
        } else {
            throw new Exception("Delivery type already exists");
        }
    }

    @Override
    public void update(int id, DeliveryType deliveryType) throws Exception {
        DeliveryType deliveryTypeToUpdate = retrieveById(id);

        deliveryTypeToUpdate.setTitle(deliveryType.getTitle());
        deliveryTypeToUpdate.setDescription(deliveryType.getDescription());

        deliveryTypeRepo.save(deliveryTypeToUpdate);
        
    }
    


}
