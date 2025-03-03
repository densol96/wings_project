package lv.wings.repo;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import lv.wings.model.Product;
import lv.wings.model.Purchase;
import lv.wings.model.PurchaseElement;

public interface IPurchaseElementRepo extends CrudRepository<PurchaseElement,Integer>, PagingAndSortingRepository<PurchaseElement, Integer>{

    ArrayList<PurchaseElement> findByPurchase(Purchase purchase);

    ArrayList<PurchaseElement> findByProduct (Product product);
	

}
