package lv.wings;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lv.wings.repo.IDeliveryTypeRepo;
import lv.wings.model.entity.Customer;
import lv.wings.model.entity.DeliveryType;
import lv.wings.model.entity.PaymentType;
import lv.wings.model.entity.Purchase;
import lv.wings.repo.ICustomerRepo;
import lv.wings.repo.IPurchaseRepo;
import lv.wings.repo.IPaymentTypeRepo;

@SpringBootTest
public class RepoFindByTest {

    @Autowired
    private ICustomerRepo customerRepo;

    @Autowired
    private IDeliveryTypeRepo deliveryTypeRepo;

    @Autowired
    private IPaymentTypeRepo paymentTypeRepo;

    @Autowired
    private IPurchaseRepo purchaseRepo;

    @Test
    @Order(3)
    public void findMarkussTest() {
        Customer markuss = new Customer("Markuss", "Blumbergs", "random1@gmail.com", "Talsi, Street 1");
        Customer found = customerRepo.findByNameAndSurname(markuss.getName(), markuss.getSurname());

        assertEquals(markuss.getName(), found.getName());
    }

    @Test
    @Order(2)
    @Disabled
    public void findDeliveryTypeTest() {
        DeliveryType deliveryType1 = new DeliveryType("Piegades Veids1", "Apraksts1");
        DeliveryType found = deliveryTypeRepo.findByTitle(deliveryType1.getTitle());

        assertEquals(deliveryType1.getTitle(), found.getTitle());
    }

    @Test
    @Order(1)
    public void findPaymentTypeTest() {
        PaymentType paymentType1 = new PaymentType("Samaksas Veids 1", "Piezimes1");
        PaymentType found = paymentTypeRepo.findByTitle(paymentType1.getTitle());

        assertEquals(paymentType1.getTitle(), found.getTitle());
    }

    @Test
    @Order(4)
    public void findPurchaseTest() {
        Customer markuss = customerRepo.findByNameAndSurname("Markuss", "Blumbergs");
        DeliveryType deliveryType1 = deliveryTypeRepo.findByTitle("Piegades Veids1");
        PaymentType paymentType1 = paymentTypeRepo.findByTitle("Samaksas Veids1");

        ArrayList<Purchase> findPirkumsWithCustomer = purchaseRepo.findByCustomer(markuss);
        ArrayList<Purchase> findPirkumsWithDeliveryType = purchaseRepo.findByDeliveryType(deliveryType1);
        ArrayList<Purchase> findPirkumsWithPaymentType = purchaseRepo.findByPaymentType(paymentType1);

        assertAll("pirkumi",
                () -> assertNotNull(findPirkumsWithCustomer),
                () -> assertNotNull(findPirkumsWithDeliveryType),
                () -> assertNotNull(findPirkumsWithPaymentType));
    }

}
