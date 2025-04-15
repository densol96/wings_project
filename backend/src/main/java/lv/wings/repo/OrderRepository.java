package lv.wings.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import lv.wings.model.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {

}
