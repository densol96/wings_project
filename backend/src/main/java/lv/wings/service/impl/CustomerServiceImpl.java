package lv.wings.service.impl;

import org.springframework.stereotype.Service;

import lv.wings.model.entity.Customer;
import lv.wings.repo.CustomerRepository;
import lv.wings.service.AbstractCRUDService;

@Service
public class CustomerServiceImpl extends AbstractCRUDService<Customer, Integer> {

    public CustomerServiceImpl(CustomerRepository customerRepo) {
        super(customerRepo, "Customer", "entity.customer");
    }
}
