package lv.wings.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import lv.wings.model.Customer;

public interface ICustomerRepo extends CrudRepository<Customer, Integer>, PagingAndSortingRepository<Customer, Integer>{

    Customer findByNameAndSurname(String name, String surname);

}
