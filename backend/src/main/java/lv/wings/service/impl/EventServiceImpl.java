package lv.wings.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lv.wings.dto.response.event.EventTranslationDto;
import lv.wings.dto.response.event.PublicEventDto;
import lv.wings.mapper.EventMapper;
import lv.wings.model.Event;
import lv.wings.model.translation.EventTranslation;
import lv.wings.repo.EventRepository;
import lv.wings.service.AbstractCRUDService;
import lv.wings.service.EventService;
import lv.wings.service.LocaleService;

@Service
public class EventServiceImpl extends AbstractCRUDService<Event, Integer> implements EventService {

    private final EventMapper eventMapper;
    private final LocaleService localeService;

    public EventServiceImpl(EventRepository eventRepository, EventMapper eventMapper, LocaleService localeService) {
        super(eventRepository, "Event", "entity.event");
        this.eventMapper = eventMapper;
        this.localeService = localeService;
    }

    @Override
    public Page<PublicEventDto> getEvents(Pageable pageable) {
        return findAll(pageable).map(this::eventToPublicDto);
    }

    @Override
    public PublicEventDto getEvent(Integer id) {
        return eventToPublicDto(findById(id));
    }

    private PublicEventDto eventToPublicDto(Event event) {
        EventTranslation translation = (EventTranslation) localeService.getRightTranslation(event);
        EventTranslationDto translationDto = eventMapper.eventTranslationToDto(translation);
        return eventMapper.eventToPublicDto(event, translationDto);
    }
}
