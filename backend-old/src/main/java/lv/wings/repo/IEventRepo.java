package lv.wings.repo;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import lv.wings.model.Event;
import lv.wings.model.EventCategory;

public interface IEventRepo extends CrudRepository<Event, Integer>, PagingAndSortingRepository<Event, Integer> {

	Event findByEventId(int id);

	ArrayList<Event> findAllByOrderByEventIdDesc();

	ArrayList<Event> findAllByOrderByEventIdAsc();

	Event findByTitle(String title);

	ArrayList<Event> findByEventCategory(EventCategory eventCategory);

}
