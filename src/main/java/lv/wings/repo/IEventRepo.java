package lv.wings.repo;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import lv.wings.model.EventCategory;
import lv.wings.model.Event;

public interface IEventRepo extends CrudRepository<Event, Integer> {

	Event findByEventId(int id);

	ArrayList<Event> findAllByOrderByEventIdDesc();

	ArrayList<Event> findAllByOrderByEventIdAsc();

	Event findByTitle(String title);

	ArrayList<Event> findByEventCategory(EventCategory eventCategory);

}
