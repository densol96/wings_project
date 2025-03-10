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

import lv.wings.model.entity.Customer;
import lv.wings.model.entity.Purchase;
import lv.wings.repo.ICustomerRepo;
import lv.wings.repo.IPurchaseRepo;
import lv.wings.service.ICRUDService;

@Service
public class CustomerServiceImpl implements ICRUDService<Customer> {

    @Autowired
    private ICustomerRepo customerRepo;

    @Autowired
    private IPurchaseRepo purchaseRepo;

    @Override
    @Cacheable("Customers")
    public List<Customer> retrieveAll() {
        return customerRepo.findAll();
    }

    @Override
    @Cacheable(value = "Customers", key = "#pageable.pageNumber + '-' + #pageable.pageSize + '-' + #pageable.sort")
    public Page<Customer> retrieveAll(Integer page, Integer size, String sortBy, String sortDirection) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDirection), sortBy));
        return customerRepo.findAll(pageable);
    }

    @Override
    @Cacheable(value = "Customers", key = "#id")
    public Customer retrieveById(Integer id) {

        if (customerRepo.existsById(id)) {
            return customerRepo.findById(id).get();
        } else {
            throw new RuntimeException("Customer with ID [" + id + "] does not exist");
        }
    }

    @Override
    @CacheEvict(value = "Customers", allEntries = true)
    public void deleteById(Integer id) {
        Customer customerToDelete = retrieveById(id);

        List<Purchase> purchases = purchaseRepo.findByCustomer(customerToDelete);

        for (Integer i = 0; i < purchases.size(); i++) {
            purchases.get(i).setCustomer(null);
            purchaseRepo.save(purchases.get(i));
        }

        customerRepo.delete(customerToDelete);
    }

    @Override
    @CacheEvict(value = "Customers", allEntries = true)
    public void create(Customer customer) {
        Customer customerExist = customerRepo.findByNameAndSurname(customer.getName(), customer.getSurname());

        if (customerExist == null) {
            customerRepo.save(customer);
        } else {
            throw new RuntimeException("Customer already exists");
        }

    }

    @Override
    @CacheEvict(value = "Customers", allEntries = true)
    @CachePut(value = "Customers", key = "#id")
    public void update(Integer id, Customer customer) {
        Customer customerToUpdate = retrieveById(id);

        customerToUpdate.setName(customer.getName());
        customerToUpdate.setSurname(customer.getSurname());
        customerToUpdate.setAdress(customer.getAdress());

        customerRepo.save(customerToUpdate);
    }

}
