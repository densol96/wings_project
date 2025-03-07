package lv.wings.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import lv.wings.model.Product;
import lv.wings.model.Purchase;
import lv.wings.model.PurchaseElement;

public interface IPurchaseElementRepo extends JpaRepository<PurchaseElement, Integer> {

    List<PurchaseElement> findByPurchase(Purchase purchase);

    List<PurchaseElement> findByProduct(Product product);

}
