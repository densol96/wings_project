package lv.wings.repo;

import org.springframework.data.repository.CrudRepository;

import lv.wings.model.DeliveryType;

public interface IDeliveryTypeRepo extends CrudRepository<DeliveryType, Integer>{

    DeliveryType findByTitle(String title);

}
