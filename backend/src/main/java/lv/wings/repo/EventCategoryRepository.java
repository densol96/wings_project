package lv.wings.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import lv.wings.model.entity.EventCategory;

public interface EventCategoryRepository extends JpaRepository<EventCategory, Integer> {
}
