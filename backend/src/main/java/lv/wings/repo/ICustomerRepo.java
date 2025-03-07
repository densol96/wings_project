package lv.wings.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import lv.wings.model.Customer;

public interface ICustomerRepo extends JpaRepository<Customer, Integer> {
    Customer findByNameAndSurname(String name, String surname);
}
