package lv.wings.service.impl;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import lombok.NonNull;
import lv.wings.enums.LocaleCode;
import lv.wings.model.entity.Event;
import lv.wings.model.entity.EventImage;
import lv.wings.model.translation.EventImageTranslation;
import lv.wings.repo.base.ImageRepository;
import lv.wings.service.AbstractImageService;
import lv.wings.service.EventService;
import lv.wings.service.LocaleService;
import lv.wings.service.shared.GenericImageProcessor;

@Service
public class EventImageServiceImpl extends AbstractImageService<EventImage, Event, EventImageTranslation, Integer> {

	private final EventService eventService;


	public EventImageServiceImpl(
			ImageRepository<EventImage, Integer> eventImageRepo,
			@Lazy EventService eventService,
			LocaleService localeService,
			GenericImageProcessor imageProcessor) {
		super(eventImageRepo,
				EventImageTranslation.class,
				"EventImage",
				"entity.event-image",
				localeService,
				imageProcessor,
				"events");

		this.eventService = eventService;
	}

	@Override
	protected EventImage getImageInstance(Event parentProduct, String url) {
		return new EventImage(parentProduct, url);
	}

	@Override
	protected EventImageTranslation createImageTranslation(LocaleCode locale, EventImage image, String title) {
		return new EventImageTranslation(locale, image, title);
	}

	@Override
	protected String getTitleFromParent(Event parentProduct, LocaleCode locale) {
		return eventService.getSelectedTranslation(parentProduct, locale).getTitle();
	}

	@Override
	protected Event getOwnerById(@NonNull Integer id) {
		return eventService.findById(id);
	}
}
