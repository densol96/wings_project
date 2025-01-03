package lv.wings.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import lv.wings.model.PaymentType;

public interface IPaymentTypeRepo extends CrudRepository<PaymentType, Integer>, PagingAndSortingRepository<PaymentType, Integer>{

    PaymentType findByTitle(String title);

}
