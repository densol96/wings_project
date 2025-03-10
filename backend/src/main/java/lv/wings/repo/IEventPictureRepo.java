package lv.wings.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import lv.wings.model.entity.EventPicture;

public interface IEventPictureRepo extends JpaRepository<EventPicture, Integer> {
	EventPicture findBySrc(String src);

	List<EventPicture> findAllByEventId(Integer id);
}
