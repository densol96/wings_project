package lv.wings.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import lv.wings.model.entity.Customer;
import lv.wings.model.entity.DeliveryType;
import lv.wings.model.entity.PaymentType;
import lv.wings.model.entity.Purchase;

public interface PurchaseRepository extends JpaRepository<Purchase, Integer> {

    List<Purchase> findByPaymentType(PaymentType paymentType);

    // List<Purchase> findByDeliveryType(DeliveryType deliveryType);

    List<Purchase> findByCustomer(Customer customer);

}
