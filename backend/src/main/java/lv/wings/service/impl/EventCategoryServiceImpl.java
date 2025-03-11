package lv.wings.service.impl;

import org.springframework.stereotype.Service;

import lv.wings.model.entity.EventCategory;
import lv.wings.repo.EventCategoryRepository;
import lv.wings.service.AbstractCRUDService;
import lv.wings.service.EventCategoryService;

@Service
public class EventCategoryServiceImpl extends AbstractCRUDService<EventCategory, Integer>
		implements EventCategoryService {

	private final EventCategoryRepository eventCategoryRepo;

	public EventCategoryServiceImpl(EventCategoryRepository eventCategoryRepo) {
		super(eventCategoryRepo, "EventCategory", "entity.event-category");
		this.eventCategoryRepo = eventCategoryRepo;
	}

}
