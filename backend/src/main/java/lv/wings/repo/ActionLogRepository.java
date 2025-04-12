package lv.wings.repo;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import lv.wings.model.entity.ActionLog;

public interface ActionLogRepository extends JpaRepository<ActionLog, Integer> {
    Optional<ActionLog> findByName(String name);
}
