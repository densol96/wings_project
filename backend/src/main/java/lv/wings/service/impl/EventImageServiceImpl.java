package lv.wings.service.impl;

import org.springframework.stereotype.Service;

import lv.wings.model.entity.Event;
import lv.wings.model.entity.EventImage;
import lv.wings.model.translation.EventImageTranslation;
import lv.wings.repo.base.ImageRepository;
import lv.wings.service.AbstractImageService;
import lv.wings.service.LocaleService;

@Service
public class EventImageServiceImpl extends AbstractImageService<EventImage, Event, EventImageTranslation, Integer> {

	public EventImageServiceImpl(ImageRepository<EventImage, Integer> eventImageRepo, LocaleService localeService) {
		super(eventImageRepo, EventImageTranslation.class, "EventImage", "entity.event-image", localeService);
	}
}
