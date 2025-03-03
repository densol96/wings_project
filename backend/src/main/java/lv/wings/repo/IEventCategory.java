package lv.wings.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import lv.wings.model.EventCategory;

public interface IEventCategory extends CrudRepository<EventCategory, Integer>, PagingAndSortingRepository<EventCategory, Integer> {

	EventCategory findByTitle(String title);

}
