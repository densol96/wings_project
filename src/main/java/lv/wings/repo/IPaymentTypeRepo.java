package lv.wings.repo;

import org.springframework.data.repository.CrudRepository;

import lv.wings.model.PaymentType;

public interface IPaymentTypeRepo extends CrudRepository<PaymentType, Integer>{

    PaymentType findByTitle(String title);

}
