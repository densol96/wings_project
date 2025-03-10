package lv.wings;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lv.wings.model.entity.Purchase;
import lv.wings.service.ICRUDService;

@SpringBootTest
public class PurchaseServiceTest {

    @Autowired
    ICRUDService<Purchase> purchaseService;

    @Test
    void selectAllPurchasesTest() throws Exception {
        ArrayList<Purchase> purchases = purchaseService.retrieveAll();
        assertNotNull(purchases);
    }

    @Test
    void selectOnePurchaseTest() throws Exception {
        Purchase purchase = purchaseService.retrieveById(1);
        assertNotNull(purchase);
    }

    @Test
    void deletePurchaseTest() throws Exception {
        purchaseService.deleteById(444);
        assertThrowsExactly(Exception.class, () -> purchaseService.retrieveById(444));
    }

    @Test
    void insertPurchaseTest() throws Exception {
        ArrayList<Purchase> oldPurchases = purchaseService.retrieveAll();
        int oldAmount = oldPurchases.size();

        Purchase purchase = new Purchase();
        purchaseService.create(purchase);

        ArrayList<Purchase> newPurchases = purchaseService.retrieveAll();
        int newAmount = newPurchases.size();

        assertNotEquals(oldAmount, newAmount);
    }

    @Test
    void updatePurchaseTest() throws Exception {
        Purchase purchase = new Purchase();
        Purchase oldPurchase = purchaseService.retrieveById(1);

        purchaseService.update(1, purchase);
        Purchase newPurchase = purchaseService.retrieveById(1);

        assertNotEquals(newPurchase, oldPurchase);
    }

}
