package lv.wings.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import lv.wings.model.DeliveryType;

public interface IDeliveryTypeRepo extends CrudRepository<DeliveryType, Integer>, PagingAndSortingRepository<DeliveryType, Integer>{

    DeliveryType findByTitle(String title);

}
