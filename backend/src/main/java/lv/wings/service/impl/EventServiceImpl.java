package lv.wings.service.impl;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lv.wings.model.Event;
import lv.wings.repo.IEventRepo;
import lv.wings.service.ICRUDService;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements ICRUDService<Event> {

	private final IEventRepo eventRepo;

	@Override
	@Cacheable("Events")
	public List<Event> retrieveAll() {
		return eventRepo.findAll();
	}

	@Override
	@Cacheable(value = "Events", key = "#pageable.pageNumber + '-' + #pageable.pageSize + '-' + #pageable.sort")
	public Page<Event> retrieveAll(Pageable pageable) {
		return eventRepo.findAll(pageable);
	}

	@Override
	@Cacheable(value = "Events", key = "#id")
	public Event retrieveById(Integer id) {
		Event foundEvent = eventRepo.findById(id)
				.orElseThrow(() -> new RuntimeException("Event with the id: (" + id + ") does not exist!"));
		return foundEvent;
	}

	@Override
	@CacheEvict(value = "Events", allEntries = true)
	public void deleteById(Integer id) throws Exception {
		Event event = eventRepo.findById(id)
				.orElseThrow(() -> new Exception("Event with id:" + id + " does not exist"));
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
	public void update(Integer id, Event event) throws Exception {
		Event foundEvent = eventRepo.findById(event.getId())
				.orElseThrow(() -> new Exception("Event with (id:" + id + ") does not exist"));

		foundEvent.setStartDate(event.getStartDate());
		foundEvent.setEndDate(event.getEndDate());
		foundEvent.setTitle(event.getTitle());
		foundEvent.setLocation(event.getLocation());
		foundEvent.setDescription(event.getDescription());
		foundEvent.setKeyWords(event.getKeyWords());

		eventRepo.save(event);
	}
}
