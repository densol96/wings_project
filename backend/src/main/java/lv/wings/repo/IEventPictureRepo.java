package lv.wings.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import lv.wings.model.EventPicture;

public interface IEventPictureRepo extends JpaRepository<EventPicture, Integer> {

	EventPicture findByImageUrl(String imageUrl);

}
