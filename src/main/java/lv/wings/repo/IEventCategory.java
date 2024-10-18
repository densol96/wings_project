package lv.wings.repo;

import org.springframework.data.repository.CrudRepository;

import lv.wings.model.EventCategory;

public interface IEventCategory extends CrudRepository<EventCategory, Integer> {

	EventCategory findByTitle(String title);

}
