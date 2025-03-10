package lv.wings.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lv.wings.dto.response.ImageDto;
import lv.wings.dto.response.event.EventTranslationShortDto;
import lv.wings.dto.response.event.ShortEventDto;
import lv.wings.mapper.EventMapper;
import lv.wings.model.entity.Event;
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
    public Page<ShortEventDto> getEvents(Pageable pageable) {
        return findAll(pageable).map(this::eventToPublicDto);
    }

    @Override
    public ShortEventDto getEvent(Integer id) {
        return eventToPublicDto(findById(id));
    }

    private ShortEventDto eventToPublicDto(Event event) {
        EventTranslation translation = (EventTranslation) localeService.getRightTranslation(event);
        EventTranslationShortDto translationDto = eventMapper.eventTranslationToShortDto(translation);
        ImageDto imageDto = eventPictureService.getEventWallpaperById(event.getId());
        return eventMapper.eventToShortDto(event, translationDto, imageDto);
    }

}
