package lv.wings.repo;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import lv.wings.model.entity.EventCategory;

public interface EventCategoryRepository extends JpaRepository<EventCategory, Integer> {
    Optional<EventCategory> findByEventsId(Integer id);
}
