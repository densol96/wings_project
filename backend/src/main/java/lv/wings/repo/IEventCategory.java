package lv.wings.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import lv.wings.model.EventCategory;

public interface IEventCategory extends JpaRepository<EventCategory, Integer> {

	EventCategory findByTitle(String title);

}
