package lv.wings.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import lv.wings.model.entity.Product;
import lv.wings.model.entity.Purchase;
import lv.wings.model.entity.PurchaseElement;

public interface PurchaseElementRepository extends JpaRepository<PurchaseElement, Integer> {

    List<PurchaseElement> findByPurchase(Purchase purchase);

    List<PurchaseElement> findByProduct(Product product);

}
