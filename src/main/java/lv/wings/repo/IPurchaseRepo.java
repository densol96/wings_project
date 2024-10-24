package lv.wings.repo;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import lv.wings.model.DeliveryType;
import lv.wings.model.Customer;
import lv.wings.model.Purchase;
import lv.wings.model.PaymentType;

public interface IPurchaseRepo extends CrudRepository<Purchase, Integer>{

    ArrayList<Purchase> findByPaymentType(PaymentType paymentType);

    ArrayList<Purchase> findByDeliveryType(DeliveryType deliveryType);

    ArrayList<Purchase> findByCustomer(Customer customer);


}
