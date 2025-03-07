package lv.wings.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import lv.wings.model.Customer;
import lv.wings.model.DeliveryType;
import lv.wings.model.PaymentType;
import lv.wings.model.Purchase;

public interface IPurchaseRepo extends JpaRepository<Purchase, Integer> {

    List<Purchase> findByPaymentType(PaymentType paymentType);

    List<Purchase> findByDeliveryType(DeliveryType deliveryType);

    List<Purchase> findByCustomer(Customer customer);

}
