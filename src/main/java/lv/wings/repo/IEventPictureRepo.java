package lv.wings.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import lv.wings.model.EventPicture;

public interface IEventPictureRepo extends CrudRepository<EventPicture, Integer>, PagingAndSortingRepository<EventPicture, Integer>{

	EventPicture findByTitle(String title);
	
	
}
