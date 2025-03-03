package lv.wings.repo;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import lv.wings.model.Customer;
import lv.wings.model.DeliveryType;
import lv.wings.model.PaymentType;
import lv.wings.model.Purchase;

public interface IPurchaseRepo extends CrudRepository<Purchase, Integer>, PagingAndSortingRepository<Purchase, Integer>{

    ArrayList<Purchase> findByPaymentType(PaymentType paymentType);

    ArrayList<Purchase> findByDeliveryType(DeliveryType deliveryType);

    ArrayList<Purchase> findByCustomer(Customer customer);


}
