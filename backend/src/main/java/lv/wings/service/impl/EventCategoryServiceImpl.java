package lv.wings.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lv.wings.model.Event;
import lv.wings.model.EventCategory;
import lv.wings.repo.IEventCategory;
import lv.wings.repo.IEventRepo;
import lv.wings.service.ICRUDService;

@Service
public class EventCategoryServiceImpl implements ICRUDService<EventCategory> {
	@Autowired
	IEventCategory eventCategoryRepo;

	@Autowired
	IEventRepo eventRepo;

	@Override
	@Cacheable("EventCategories")
	public List<EventCategory> retrieveAll() throws Exception {
		if (eventCategoryRepo.count() == 0)
			throw new Exception("There are no event categories in the database");

		return eventCategoryRepo.findAll();
	}

	@Override
	@Cacheable(value = "EventCategories", key = "#pageable.pageNumber + '-' + #pageable.pageSize + '-' + #pageable.sort")
	public Page<EventCategory> retrieveAll(Pageable pageable) throws Exception {
		if (eventCategoryRepo.count() == 0)
			throw new Exception("There are no event categories in the database");
		return eventCategoryRepo.findAll(pageable);
	}

	@Override
	@Cacheable(value = "EventCategories", key = "#id")
	public EventCategory retrieveById(Integer id) throws Exception {
		if (id < 1)
			throw new Exception("Invalid ID!");

		if (eventCategoryRepo.existsById(id)) {
			return eventCategoryRepo.findById(id).get();
		} else {
			throw new Exception("Event category with the id: (" + id + ") does not exist!");
		}
	}

	@Override
	@CacheEvict(value = "EventCategories", allEntries = true)
	public void deleteById(Integer id) throws Exception {
		EventCategory eventCategory = retrieveById(id);

		if (eventCategory == null)
			throw new Exception("Event category with the id: (" + id + ") does not exist!");

		List<Event> events = eventRepo.findByCategory(eventCategory);

		for (Integer i = 0; i < events.size(); i++) {
			events.get(i).setCategory(null);
			eventRepo.save(events.get(i));
		}

		eventCategoryRepo.delete(eventCategory);

	}

	@Override
	@CacheEvict(value = "EventCategories", allEntries = true)
	public void create(EventCategory eventCategory) throws Exception {
		EventCategory existedPaskumaKategorija = eventCategoryRepo
				.findByTitle(eventCategory.getTitle());

		if (existedPaskumaKategorija != null)
			throw new Exception(
					"Event category with title: " + eventCategory.getTitle() + " already exists");

		eventCategoryRepo.save(eventCategory);

	}

	@Override
	@CacheEvict(value = "EventCategories", allEntries = true)
	@CachePut(value = "EventCategories", key = "#id")
	public void update(Integer id, EventCategory eventCategory) throws Exception {
		EventCategory foundEventCategory = retrieveById(id);
		if (foundEventCategory == null)
			throw new Exception("Event category with the id: (" + id + ") does not exist!");

		foundEventCategory.setTitle(eventCategory.getTitle());
		eventCategoryRepo.save(foundEventCategory);
	}
}
