package lv.wings.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lv.wings.model.PaymentType;
import lv.wings.model.Purchase;
import lv.wings.repo.IPaymentTypeRepo;
import lv.wings.repo.IPurchaseRepo;
import lv.wings.service.ICRUDService;


@Service
public class PaymentTypeServiceImpl implements ICRUDService<PaymentType> {

    @Autowired 
    private IPaymentTypeRepo paymentTypeRepo;

    @Autowired 
    private IPurchaseRepo purchaseRepo;


    @Override
    public ArrayList<PaymentType> retrieveAll() throws Exception {
        if(paymentTypeRepo.count() == 0) throw new Exception("Nav neviena samaksas veida");

        return (ArrayList<PaymentType>) paymentTypeRepo.findAll();
    }

    @Override
    public Page<PaymentType> retrieveAll(Pageable pageable) throws Exception {
        if(paymentTypeRepo.count() == 0) throw new Exception("Nav neviena samaksas veida");
        return (Page<PaymentType>) paymentTypeRepo.findAll(pageable);
    }

    @Override
    public PaymentType retrieveById(int id) throws Exception {
        if(id < 0) throw new Exception("ID ir negativs");

        if(paymentTypeRepo.existsById(id)){
            return paymentTypeRepo.findById(id).get();
        } else {
            throw new Exception("Samaksas veids ar ID [" + id + "] neeksiste");
        }
    }

    @Override
    public void deleteById(int id) throws Exception {
        PaymentType paymentTypeToDelete = retrieveById(id);

        ArrayList<Purchase> purchases = purchaseRepo.findByPaymentType(paymentTypeToDelete);
        
        for(int i = 0; i < purchases.size(); i++) {
            purchases.get(i).setPaymentType(null);
            purchaseRepo.save(purchases.get(i));
        }

        paymentTypeRepo.delete(paymentTypeToDelete);
    }

    @Override
    public void create(PaymentType paymentType) throws Exception {
        PaymentType paymentTypeExist = paymentTypeRepo.findByTitle(paymentType.getTitle());

        if(paymentTypeExist == null) {
            paymentTypeRepo.save(paymentType);
        } else {
            throw new Exception("Samaksas veids eksiste");
        }
    }

    @Override
    public void update(int id, PaymentType paymentType) throws Exception{
        PaymentType paymentTypeToUpdate = retrieveById(id);

        paymentTypeToUpdate.setTitle(paymentType.getTitle());
        paymentTypeToUpdate.setDescription(paymentType.getDescription());
        
        paymentTypeRepo.save(paymentTypeToUpdate);


    }
    
}
