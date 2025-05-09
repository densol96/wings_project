package lv.wings.repo;

import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import io.lettuce.core.dynamic.annotation.Param;
import lv.wings.enums.SecurityEventType;
import lv.wings.model.security.SecurityEvent;
import lv.wings.model.security.User;

public interface SecurityEventRepository extends JpaRepository<SecurityEvent, Integer> {
    boolean existsByUserAndEventTypeAndDateTimeAfter(User user, SecurityEventType securirtyEventType, LocalDateTime dateTime);

    Page<SecurityEvent> findAll(Pageable pageable);

    Page<SecurityEvent> findAllByEventType(SecurityEventType eventType, Pageable pageable);

    @Query("""
                SELECT e FROM SecurityEvent e
                WHERE LOWER(e.user.username) LIKE LOWER(CONCAT('%', :q, '%'))
                   OR LOWER(e.additionalInfo) LIKE LOWER(CONCAT('%', :q, '%'))
            """)
    Page<SecurityEvent> search(@Param("q") String q, Pageable pageable);

    @Query("""
                SELECT e FROM SecurityEvent e
                WHERE (
                    LOWER(e.user.username) LIKE LOWER(CONCAT('%', :q, '%'))
                   OR LOWER(e.user.email) LIKE LOWER(CONCAT('%', :q, '%'))
                   OR LOWER(e.additionalInfo) LIKE LOWER(CONCAT('%', :q, '%'))
                )
                AND e.eventType = :eventType
            """)
    Page<SecurityEvent> search(@Param("q") String q, @Param("eventType") SecurityEventType eventType, Pageable pageable);


}
