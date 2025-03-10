package lv.wings.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import lv.wings.model.entity.Customer;
import lv.wings.model.entity.DeliveryType;

public interface IDeliveryTypeRepo extends JpaRepository<DeliveryType, Integer> {

    DeliveryType findByTitle(String title);

}
