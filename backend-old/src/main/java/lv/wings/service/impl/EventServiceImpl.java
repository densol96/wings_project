package lv.wings.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lv.wings.exceptions.NoContentException;
import lv.wings.model.Event;
import lv.wings.repo.IEventRepo;
import lv.wings.service.ICRUDService;
import lv.wings.service.IPasakumiFilteringService;

@Service
public class EventServiceImpl implements ICRUDService<Event>, IPasakumiFilteringService {

	@Autowired
	private IEventRepo eventRepo;

	@Override
	@Cacheable("Events")
	public ArrayList<Event> retrieveAll() throws NoContentException {
		ArrayList<Event> events = (ArrayList<Event>) eventRepo.findAll();
		if (events.isEmpty())
			throw new NoContentException("There are no events");
		return events;
	}

	@Override
	@Cacheable(value = "Events", key = "#pageable.pageNumber + '-' + #pageable.pageSize + '-' + #pageable.sort")
	public Page<Event> retrieveAll(Pageable pageable) throws NoContentException {
		Page<Event> events = (Page<Event>) eventRepo.findAll(pageable);
		if (events.isEmpty())
			throw new NoContentException("There are no events");

		return events;
	}

	@Override
	@Cacheable(value = "Events", key = "#id")
	public Event retrieveById(int id) throws Exception {
		if (id < 1)
			throw new Exception("Invalid ID");
		Event foundEvent = eventRepo.findByEventId(id);
		if (foundEvent == null)
			throw new Exception("Event with the id: (" + id + ") does not exist!");

		return foundEvent;
	}

	@Override
	@Cacheable(value = "Events", key = "'desc'")
	public ArrayList<Event> selectAllEventsDescOrder() throws Exception {
		ArrayList<Event> events = (ArrayList<Event>) eventRepo.findAllByOrderByEventIdDesc();

		if (events.isEmpty())
			throw new Exception("There are no events");
		return events;
	}

	@Override
	@Cacheable(value = "Events", key = "'asc'")
	public ArrayList<Event> selectAllEventsAscOrder() throws Exception {
		ArrayList<Event> events = (ArrayList<Event>) eventRepo.findAllByOrderByEventIdAsc();
		if (events.isEmpty())
			throw new Exception("There are no events");
		return events;
	}

	@Override
	@CacheEvict(value = "Events", allEntries = true)
	public void deleteById(int id) throws Exception {
		Event event = eventRepo.findByEventId(id);
		if (event == null)
			throw new Exception("Event with id:" + id + " does not exist");

		eventRepo.delete(event);
	}

	@Override
	@CacheEvict(value = "Events", allEntries = true)
	public void create(Event event) throws Exception {
		Event existedEvent = eventRepo.findByTitle(event.getTitle());

		if (existedEvent != null)
			throw new Exception("Event with title: " + event.getTitle() + " already exists");

		eventRepo.save(event);
	}

	@Override
	@CacheEvict(value = "Events", allEntries = true)
	@CachePut(value = "Events", key = "#id")
	public void update(int id, Event event) throws Exception {
		Event foundEvent = eventRepo.findByEventId(event.getEventId());

		if (foundEvent == null)
			throw new Exception("Event with (id:" + id + ") does not exist");

		foundEvent.setStartDate(event.getStartDate());
		foundEvent.setEndDate(event.getEndDate());
		foundEvent.setTitle(event.getTitle());
		foundEvent.setLocation(event.getLocation());
		foundEvent.setDescription(event.getDescription());
		foundEvent.setKeyWords(event.getKeyWords());

		eventRepo.save(event);
	}
}
