package lv.wings.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lv.wings.model.Customer;
import lv.wings.model.Purchase;
import lv.wings.repo.ICustomerRepo;
import lv.wings.repo.IPurchaseRepo;
import lv.wings.service.ICRUDService;

@Service
public class CustomerServiceImpl implements ICRUDService<Customer>{

    @Autowired
    private ICustomerRepo customerRepo;

    @Autowired 
    private IPurchaseRepo purchaseRepo;

    @Override
    public ArrayList<Customer> retrieveAll() throws Exception {
       if(customerRepo.count() == 0) throw new Exception("There are no customers");

        return (ArrayList<Customer>) customerRepo.findAll();
    }

    @Override
    public Customer retrieveById(int id) throws Exception {
        if(id < 0) throw new Exception("Invalid ID!");

        if(customerRepo.existsById(id)){
            return customerRepo.findById(id).get();
        } else {
            throw new Exception("Customer with ID [" + id + "] does not exist");
        }
    }

    @Override
    public void deleteById(int id) throws Exception {
        Customer customerToDelete = retrieveById(id);

        ArrayList<Purchase> purchases = purchaseRepo.findByCustomer(customerToDelete);
        
        for(int i = 0; i < purchases.size(); i++) {
            purchases.get(i).setCustomer(null);
            purchaseRepo.save(purchases.get(i));
        }

        customerRepo.delete(customerToDelete);
    }

    @Override
    public void create(Customer customer) throws Exception {
       Customer customerExist = customerRepo.findByNameAndSurname(customer.getName(), customer.getSurname());

        if(customerExist == null) {
            customerRepo.save(customer);
        } else {
            throw new Exception("Customer already exists");
        }

    }

    @Override
    public void update(int id, Customer customer) throws Exception {
        Customer customerToUpdate = retrieveById(id);

        customerToUpdate.setName(customer.getName());
        customerToUpdate.setSurname(customer.getSurname());
        customerToUpdate.setAdress(customer.getAdress());

        customerRepo.save(customerToUpdate);
    }
    
}
