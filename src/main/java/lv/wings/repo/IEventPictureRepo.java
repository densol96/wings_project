package lv.wings.repo;

import org.springframework.data.repository.CrudRepository;

import lv.wings.model.EventPicture;

public interface IEventPictureRepo extends CrudRepository<EventPicture, Integer>{

	EventPicture findByTitle(String title);
	
	
}
