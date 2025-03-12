package lv.wings.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import lv.wings.model.entity.DeliveryType;

public interface DeliveryTypeRepository extends JpaRepository<DeliveryType, Integer> {

}
