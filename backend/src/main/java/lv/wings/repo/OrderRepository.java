package lv.wings.repo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import lv.wings.enums.Country;
import lv.wings.enums.DeliveryMethod;
import lv.wings.enums.OrderStatus;
import lv.wings.model.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    Optional<Order> findByPaymentIntentId(String paymentIntentId);

    @Query("SELECT o FROM Order o LEFT JOIN FETCH o.orderItems WHERE o.paymentIntentId = :paymentIntentId")
    Optional<Order> findByPaymentIntentIdWithItems(@Param("paymentIntentId") String paymentIntentId);

    List<Order> findAllByStatusAndCreatedAtBefore(OrderStatus status, LocalDateTime threshold);

    @Query("""
            SELECT o FROM Order o
            LEFT JOIN o.customer c
            LEFT JOIN c.address a
            WHERE
                (:status IS NULL OR o.status = :status)
                AND (:toCountry IS NULL OR o.deliveryVariation.country = :toCountry)
                AND (:deliveryMethod IS NULL OR o.deliveryVariation.deliveryType.method = :deliveryMethod)
                AND (
                    :q IS NULL OR (
                        LOWER(c.firstName) LIKE LOWER(CONCAT('%', :q, '%')) OR
                        LOWER(c.lastName) LIKE LOWER(CONCAT('%', :q, '%')) OR
                        LOWER(c.email) LIKE LOWER(CONCAT('%', :q, '%')) OR
                        LOWER(c.additionalDetails) LIKE LOWER(CONCAT('%', :q, '%')) OR
                        LOWER(a.street) LIKE LOWER(CONCAT('%', :q, '%')) OR
                        LOWER(a.houseNumber) LIKE LOWER(CONCAT('%', :q, '%')) OR
                        LOWER(a.apartment) LIKE LOWER(CONCAT('%', :q, '%')) OR
                        LOWER(a.city) LIKE LOWER(CONCAT('%', :q, '%')) OR
                        LOWER(a.postalCode) LIKE LOWER(CONCAT('%', :q, '%')) OR
                        LOWER(o.terminal.name) LIKE LOWER(CONCAT('%', :q, '%')) OR
                        LOWER(o.terminal.address) LIKE LOWER(CONCAT('%', :q, '%'))
                    )
                )
            """)
    Page<Order> findFilteredOrders(
            @Param("status") OrderStatus status,
            @Param("toCountry") Country toCountry,
            @Param("deliveryMethod") DeliveryMethod deliveryMethod,
            @Param("q") String q,
            Pageable pageable);

    @Query("SELECT o FROM Order o WHERE (o.lastModifiedAt < :cutoff OR o.lastModifiedAt IS NULL) AND o.status = PAID")
    List<Order> findOrdersThatCanBeCompleted(@Param("cutoff") LocalDateTime cutoff);

    @Query("""
            SELECT o FROM Order o
            LEFT JOIN o.customer c
            LEFT JOIN c.address a
            LEFT JOIN o.terminal t
            WHERE (:status IS NULL OR o.status = :status)
            AND (:toCountry IS NULL OR o.deliveryVariation.country = :toCountry)
            AND (:deliveryMethod IS NULL OR o.deliveryVariation.deliveryType.method = :deliveryMethod)
            AND (:q IS NULL OR (
                LOWER(c.firstName) LIKE LOWER(CONCAT('%', :q, '%')) OR
                LOWER(c.lastName) LIKE LOWER(CONCAT('%', :q, '%')) OR
                LOWER(c.email) LIKE LOWER(CONCAT('%', :q, '%')) OR
                LOWER(c.additionalDetails) LIKE LOWER(CONCAT('%', :q, '%')) OR
                LOWER(a.street) LIKE LOWER(CONCAT('%', :q, '%')) OR
                LOWER(a.houseNumber) LIKE LOWER(CONCAT('%', :q, '%')) OR
                LOWER(a.apartment) LIKE LOWER(CONCAT('%', :q, '%')) OR
                LOWER(a.city) LIKE LOWER(CONCAT('%', :q, '%')) OR
                LOWER(a.postalCode) LIKE LOWER(CONCAT('%', :q, '%')) OR
                LOWER(t.name) LIKE LOWER(CONCAT('%', :q, '%')) OR
                LOWER(t.address) LIKE LOWER(CONCAT('%', :q, '%'))
                    )
                )
            """)
    Page<Order> findFilteredOrders(
            Pageable pageable,
            @Param("status") OrderStatus status,
            @Param("toCountry") Country toCountry,
            @Param("deliveryMethod") DeliveryMethod deliveryMethod,
            @Param("q") String q);


}
