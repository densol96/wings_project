package lv.wings.service.impl;

import org.springframework.stereotype.Service;

import lv.wings.mapper.EventMapper;
import lv.wings.model.entity.Event;
import lv.wings.model.entity.EventCategory;
import lv.wings.model.translation.EventCategoryTranslation;
import lv.wings.repo.EventCategoryRepository;
import lv.wings.service.AbstractTranslatableCRUDService;
import lv.wings.service.EventCategoryService;
import lv.wings.service.LocaleService;

@Service
public class EventCategoryServiceImpl extends AbstractTranslatableCRUDService<EventCategory, EventCategoryTranslation, Integer>
		implements EventCategoryService {

	private final EventCategoryRepository eventCategoryRepo;
	private final EventMapper eventMapper;

	public EventCategoryServiceImpl(EventCategoryRepository eventCategoryRepo, EventMapper eventMapper, LocaleService localeService) {
		super(eventCategoryRepo, "EventCategory", "entity.event-category", localeService);
		this.eventCategoryRepo = eventCategoryRepo;
		this.eventMapper = eventMapper;
	}

	@Override
	public String getCategoryTitleByEvent(Event event) {
		return getRightTranslation(event.getCategory(), EventCategoryTranslation.class).getTitle();
	}

}
