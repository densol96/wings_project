package lv.wings.repo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import lv.wings.enums.OrderStatus;
import lv.wings.model.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    Optional<Order> findByPaymentIntentId(String paymentIntentId);

    @Query("SELECT o FROM Order o LEFT JOIN FETCH o.orderItems WHERE o.paymentIntentId = :paymentIntentId")
    Optional<Order> findByPaymentIntentIdWithItems(@Param("paymentIntentId") String paymentIntentId);

    List<Order> findAllByStatusAndCreatedAtBefore(OrderStatus status, LocalDateTime threshold);
}
