package lv.wings.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import lv.wings.model.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Customer findByNameAndSurname(String name, String surname);
}
