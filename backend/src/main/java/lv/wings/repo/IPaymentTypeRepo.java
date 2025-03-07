package lv.wings.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import lv.wings.model.PaymentType;

public interface IPaymentTypeRepo extends JpaRepository<PaymentType, Integer> {

    PaymentType findByTitle(String title);

}
