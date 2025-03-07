package lv.wings.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import lv.wings.model.Event;
import lv.wings.model.EventCategory;

public interface IEventRepo extends JpaRepository<Event, Integer> {
	List<Event> findAllByOrderByIdDesc();

	List<Event> findAllByOrderByIdAsc();

	Event findByTitle(String title);

	List<Event> findByCategory(EventCategory category);
}
