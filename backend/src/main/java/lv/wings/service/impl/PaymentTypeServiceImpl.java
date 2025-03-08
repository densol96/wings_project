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
    @Cacheable("PaymentTypes")
    public ArrayList<PaymentType> retrieveAll() {
        if (paymentTypeRepo.count() == 0)
            throw new RuntimeException("Nav neviena samaksas veida");

        return (ArrayList<PaymentType>) paymentTypeRepo.findAll();
    }

    @Override
    @Cacheable(value = "PaymentTypes", key = "#pageable.pageNumber + '-' + #pageable.pageSize + '-' + #pageable.sort")
    public Page<PaymentType> retrieveAll(Integer page, Integer size, String sortBy, String sortDirection) {
        if (paymentTypeRepo.count() == 0)
            throw new RuntimeException("Nav neviena samaksas veida");
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDirection), sortBy));
        return (Page<PaymentType>) paymentTypeRepo.findAll(pageable);
    }

    @Override
    @Cacheable(value = "PaymentTypes", key = "#id")
    public PaymentType retrieveById(Integer id) {
        if (id < 0)
            throw new RuntimeException("ID ir negativs");

        if (paymentTypeRepo.existsById(id)) {
            return paymentTypeRepo.findById(id).get();
        } else {
            throw new RuntimeException("Samaksas veids ar ID [" + id + "] neeksiste");
        }
    }

    @Override
    @CacheEvict(value = "PaymentTypes", allEntries = true)
    public void deleteById(Integer id) {
        PaymentType paymentTypeToDelete = retrieveById(id);

        List<Purchase> purchases = purchaseRepo.findByPaymentType(paymentTypeToDelete);

        for (Integer i = 0; i < purchases.size(); i++) {
            purchases.get(i).setPaymentType(null);
            purchaseRepo.save(purchases.get(i));
        }

        paymentTypeRepo.delete(paymentTypeToDelete);
    }

    @Override
    @CacheEvict(value = "PaymentTypes", allEntries = true)
    public void create(PaymentType paymentType) {
        PaymentType paymentTypeExist = paymentTypeRepo.findByTitle(paymentType.getTitle());

        if (paymentTypeExist == null) {
            paymentTypeRepo.save(paymentType);
        } else {
            throw new RuntimeException("Samaksas veids eksiste");
        }
    }

    @Override
    @CacheEvict(value = "PaymentTypes", allEntries = true)
    @CachePut(value = "PaymentTypes", key = "#id")
    public void update(Integer id, PaymentType paymentType) {
        PaymentType paymentTypeToUpdate = retrieveById(id);

        paymentTypeToUpdate.setTitle(paymentType.getTitle());
        paymentTypeToUpdate.setDescription(paymentType.getDescription());

        paymentTypeRepo.save(paymentTypeToUpdate);
    }
}
