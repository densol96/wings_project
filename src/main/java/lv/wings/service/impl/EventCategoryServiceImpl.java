package lv.wings.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lv.wings.model.EventCategory;
import lv.wings.model.Event;
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
	public ArrayList<EventCategory> retrieveAll() throws Exception {
		if (eventCategoryRepo.count() == 0)
			throw new Exception("There are no event categories in the database");

		return (ArrayList<EventCategory>) eventCategoryRepo.findAll();
	}

	@Override
	public EventCategory retrieveById(int id) throws Exception {
		if (id < 1)
			throw new Exception("Invalid ID!");

		if (eventCategoryRepo.existsById(id)) {
			return eventCategoryRepo.findById(id).get();
		} else {
			throw new Exception("Event category with the id: (" + id + ") does not exist!");
		}
	}

	@Override
	public void deleteById(int id) throws Exception {
		EventCategory eventCategory = retrieveById(id);
		
		if (eventCategory == null) throw new Exception("Event category with the id: (" + id + ") does not exist!");

		ArrayList<Event> events = eventRepo.findByEventCategory(eventCategory);

		for(int i = 0; i < events.size(); i++) {
			events.get(i).setEventCategory(null);
			eventRepo.save(events.get(i));
		}

		eventCategoryRepo.delete(eventCategory);

	}

	@Override
	public void create(EventCategory eventCategory) throws Exception {
		EventCategory existedPaskumaKategorija = eventCategoryRepo
				.findByTitle(eventCategory.getTitle());

		if (existedPaskumaKategorija != null)
			throw new Exception(
					"Event category with title: " + eventCategory.getTitle() + " already exists");

		eventCategoryRepo.save(eventCategory);

	}

	@Override
	public void update(int id, EventCategory eventCategory) throws Exception {
		EventCategory foundEventCategory = retrieveById(id);
		if (foundEventCategory == null) throw new Exception("Event category with the id: (" + id + ") does not exist!");

		foundEventCategory.setTitle(eventCategory.getTitle());

		eventCategoryRepo.save(foundEventCategory);

	}

}
