package lv.wings.repo;

import org.springframework.data.repository.CrudRepository;

import lv.wings.model.Customer;

public interface ICustomerRepo extends CrudRepository<Customer, Integer>{

    Customer findByNameAndSurname(String name, String surname);

}
