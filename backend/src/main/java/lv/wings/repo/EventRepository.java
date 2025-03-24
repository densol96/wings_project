package lv.wings.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import lv.wings.model.entity.Event;
import lv.wings.model.entity.EventCategory;

public interface EventRepository extends JpaRepository<Event, Integer> {
	List<Event> findAllByOrderByIdDesc();

	List<Event> findAllByOrderByIdAsc();

	// Event findByTitle(String title);

	List<Event> findByCategory(EventCategory category);
}
