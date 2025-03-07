package lv.wings.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lv.wings.model.EventPicture;
import lv.wings.repo.IEventPictureRepo;
import lv.wings.service.ICRUDService;

@Service
public class EventPictureServiceImpl implements ICRUDService<EventPicture> {
	@Autowired
	private IEventPictureRepo eventPictureRepo;

	@Override
	@Cacheable("EventPictures")
	public ArrayList<EventPicture> retrieveAll() throws Exception {
		if (eventPictureRepo.count() == 0)
			throw new Exception("There are no event pictures in the database");

		return (ArrayList<EventPicture>) eventPictureRepo.findAll();
	}

	@Override
	@Cacheable(value = "EventPictures", key = "#pageable.pageNumber + '-' + #pageable.pageSize + '-' + #pageable.sort")
	public Page<EventPicture> retrieveAll(Pageable pageable) throws Exception {
		if (eventPictureRepo.count() == 0)
			throw new Exception("There are no event pictures in the database");
		return (Page<EventPicture>) eventPictureRepo.findAll(pageable);
	}

	@Override
	@Cacheable(value = "EventPictures", key = "#id")
	public EventPicture retrieveById(Integer id) throws Exception {
		if (id < 1)
			throw new Exception("Invalid ID");

		if (eventPictureRepo.existsById(id)) {
			return eventPictureRepo.findById(id).get();
		} else {
			throw new Exception("Event picture with the id: (" + id + ") does not exist!");
		}
	}

	@Override
	@CacheEvict(value = "EventPictures", allEntries = true)
	public void deleteById(Integer id) throws Exception {
		EventPicture eventPicture = retrieveById(id);
		if (eventPicture == null)
			throw new Exception("Event picture with the id: (" + id + ") does not exist!");

		// System.out.prIntegerln(eventPicture.getReferenceToPicture());
		eventPictureRepo.delete(eventPicture);
	}

	@Override
	@CacheEvict(value = "EventPictures", allEntries = true)
	public void create(EventPicture eventPicture) throws Exception {
		EventPicture existedEventPicture = eventPictureRepo.findByImageUrl(eventPicture.getImageUrl());

		if (existedEventPicture != null)
			throw new Exception("Event picture with title: " + eventPicture.getTitle() + " already exists");

		eventPictureRepo.save(eventPicture);

	}

	@Override
	@CacheEvict(value = "EventPictures", allEntries = true)
	@CachePut(value = "EventPictures", key = "#id")
	public void update(Integer id, EventPicture eventPicture) throws Exception {
		EventPicture foundEventPicture = retrieveById(id);

		if (foundEventPicture == null)
			throw new Exception("Event picture with the id: (" + id + ") does not exist!");

		foundEventPicture.setImageUrl(eventPicture.getImageUrl());
		foundEventPicture.setTitle(eventPicture.getTitle());
		foundEventPicture.setDescription(eventPicture.getDescription());

		eventPictureRepo.save(foundEventPicture);

	}

}
