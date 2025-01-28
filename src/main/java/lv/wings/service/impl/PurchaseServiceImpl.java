package lv.wings.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lv.wings.model.Purchase;
import lv.wings.model.PurchaseElement;
import lv.wings.repo.IPurchaseElementRepo;
import lv.wings.repo.IPurchaseRepo;
import lv.wings.service.ICRUDService;

@Service
public class PurchaseServiceImpl implements ICRUDService<Purchase>{
    
    @Autowired
    private IPurchaseRepo purchaseRepo;

    @Autowired
	private IPurchaseElementRepo elementRepo;


    @Override
    @Cacheable("cachenameloc")
    public ArrayList<Purchase> retrieveAll() throws Exception {
        if(purchaseRepo.count() == 0) throw new Exception("There are no purchases");

        return (ArrayList<Purchase>) purchaseRepo.findAll();
    }

    @Override
    @Cacheable(value = "cachenameloc", key = "#pageable.pageNumber + '-' + #pageable.pageSize + '-' + #pageable.sort")
    public Page<Purchase> retrieveAll(Pageable pageable) throws Exception {
        if(purchaseRepo.count() == 0) throw new Exception("There are no purchases");
        return (Page<Purchase>) purchaseRepo.findAll(pageable);
    }

    @Override
    @Cacheable(value="cachenameloc", key="#id")
    public Purchase retrieveById(int id) throws Exception {
        
        if(id < 0) throw new Exception("Invalid ID");

        if(purchaseRepo.existsById(id)){
            return purchaseRepo.findById(id).get();
        } else {
            throw new Exception("Purchase with id [" + id + "] does not exist");
        }

    }


    @Override
    @CacheEvict(value = "cachenameloc", allEntries = true)
    public void deleteById(int id) throws Exception {
        Purchase purchaseToDelete = retrieveById(id);

        ArrayList<PurchaseElement> purchaseElements = elementRepo.findByPurchase(purchaseToDelete);
        
        for(int i = 0; i < purchaseElements.size(); i++) {
            purchaseElements.get(i).setPurchase(null);
            elementRepo.save(purchaseElements.get(i));
        }

        purchaseRepo.delete(purchaseToDelete);
    }


    @Override
    @CacheEvict(value = "cachenameloc", allEntries = true)
    public void create(Purchase purchase) throws Exception {
        
        purchaseRepo.save(purchase);

    }


    @Override
    @CacheEvict(value = "cachenameloc", allEntries = true)
	@CachePut(value="cachenameloc", key="#id")
	public void update(int id, Purchase purchase) {
        



    }

}
