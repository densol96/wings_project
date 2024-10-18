package lv.wings.repo;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import lv.wings.model.PurchaseElement;
import lv.wings.model.Purchase;
import lv.wings.model.Product;

public interface IPurchaseElementRepo extends CrudRepository<PurchaseElement,Integer>{

    ArrayList<PurchaseElement> findByPurchase(Purchase purchase);

    ArrayList<PurchaseElement> findByProduct (Product product);
	

}
