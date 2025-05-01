package lv.wings.repo;

import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import lv.wings.enums.SecurityEventType;
import lv.wings.model.security.SecurityEvent;
import lv.wings.model.security.User;

public interface SecurityEventRepository extends JpaRepository<SecurityEvent, Integer> {
    boolean existsByUserAndEventTypeAndDateTimeAfter(User user, SecurityEventType securirtyEventType, LocalDateTime dateTime);
}
