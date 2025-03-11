package lv.wings.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lv.wings.dto.response.ImageDto;
import lv.wings.dto.response.event.EventTranslationDto;
import lv.wings.dto.response.event.EventTranslationShortDto;
import lv.wings.dto.response.event.ShortEventDto;
import lv.wings.dto.response.event.SingleEventDto;
import lv.wings.exception.entity.MissingTranslationException;
import lv.wings.mapper.EventMapper;
import lv.wings.model.entity.Event;
import lv.wings.model.interfaces.Localable;
import lv.wings.model.translation.EventTranslation;
import lv.wings.repo.EventRepository;
import lv.wings.service.AbstractCRUDService;
import lv.wings.service.EventService;
import lv.wings.service.LocaleService;

@Service
public class EventServiceImpl extends AbstractCRUDService<Event, Integer> implements EventService {

    private final EventMapper eventMapper;
    private final LocaleService localeService;
    private final EventPictureServiceImpl eventPictureService;

    public EventServiceImpl(
            EventRepository eventRepository,
            EventMapper eventMapper,
            EventPictureServiceImpl eventPictureService,
            LocaleService localeService) {
        super(eventRepository, "Event", "entity.event");
        this.eventMapper = eventMapper;
        this.localeService = localeService;
        this.eventPictureService = eventPictureService;
    }

    @Override
    public Page<ShortEventDto> getPublicEvents(Pageable pageable) {
        return findAll(pageable).map(this::eventToShortPublicDto);
    }

    @Override
    public SingleEventDto getPublicEvent(Integer id) {
        Event event = findById(id);
        EventTranslationDto translation = eventMapper.eventTranslationToDto(getRightTranslationPerLocale(event));
        return eventMapper.eventToFullDto(event, translation);
    }

    private EventTranslation getRightTranslationPerLocale(Event event) {
        return localeService.getRightTranslation(
                event,
                EventTranslation.class,
                () -> new MissingTranslationException("entity.event", "Event", event.getId()));
    }

    private ShortEventDto eventToShortPublicDto(Event event) {
        EventTranslation translation = getRightTranslationPerLocale(event);
        EventTranslationShortDto translationShortDto = eventMapper.eventTranslationToShortDto(translation);
        ImageDto image = eventPictureService.getEventWallpaperById(event.getId());
        return eventMapper.eventToShortDto(event, translationShortDto, image);
    }

}
