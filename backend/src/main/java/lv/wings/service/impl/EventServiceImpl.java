package lv.wings.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lv.wings.dto.response.ImageDto;
import lv.wings.dto.response.event.EventTranslationDto;
import lv.wings.dto.response.event.SearchedEventDto;
import lv.wings.dto.response.event.ShortEventDto;
import lv.wings.dto.response.event.ShortEventTranslationDto;
import lv.wings.dto.response.event.SingleEventDto;
import lv.wings.dto.response.product.SearchedProductDto;
import lv.wings.mapper.EventMapper;
import lv.wings.model.entity.Event;
import lv.wings.model.entity.EventImage;
import lv.wings.model.translation.EventTranslation;
import lv.wings.repo.EventRepository;
import lv.wings.repo.EventTranslationRepository;
import lv.wings.service.AbstractTranslatableCRUDService;
import lv.wings.service.EventCategoryService;
import lv.wings.service.EventService;
import lv.wings.service.ImageService;
import lv.wings.service.LocaleService;

@Service
public class EventServiceImpl extends AbstractTranslatableCRUDService<Event, EventTranslation, Integer> implements EventService {

    private final EventTranslationRepository eventTranslationRepository;
    private final EventMapper eventMapper;
    private final ImageService<EventImage, Integer> eventImageService;
    private final EventCategoryService eventCategoryService;

    public EventServiceImpl(
            EventRepository eventRepository,
            EventTranslationRepository eventTranslationRepository,
            EventMapper eventMapper,
            ImageService<EventImage, Integer> eventImageService,
            LocaleService localeService,
            EventCategoryService eventCategoryService) {
        super(eventRepository, "Event", "entity.event", localeService);
        this.eventTranslationRepository = eventTranslationRepository;
        this.eventMapper = eventMapper;
        this.eventImageService = eventImageService;
        this.eventCategoryService = eventCategoryService;
    }

    @Override
    public Page<ShortEventDto> getPublicEvents(Pageable pageable) {
        return findAll(pageable).map(this::eventToShortPublicDto);
    }

    @Override
    public SingleEventDto getPublicEvent(Integer id) {
        Event event = findById(id);
        EventTranslationDto translation = eventMapper.eventTranslationToDto(getRightTranslation(event, EventTranslation.class));
        String category = eventCategoryService.getCategoryTitleByEvent(event);
        List<ImageDto> images = eventImageService.getImagesAsDtoPerOwnerId(id);
        return eventMapper.eventToFullDto(event, translation, images, category);
    }

    @Override
    public List<SearchedEventDto> getSearchedEvents(String q) {
        if (q.isBlank())
            return new ArrayList<>();

        return eventTranslationRepository.findByTitleContainingIgnoreCaseAndLocaleEquals(q, localeService.getCurrentLocaleCode()).stream()
                .map(this::translationToSearchedEventDto)
                .toList();
    }

    private ShortEventDto eventToShortPublicDto(Event event) {
        EventTranslation translation = getRightTranslation(event, EventTranslation.class);
        ShortEventTranslationDto translationShortDto = eventMapper.eventTranslationToShortDto(translation);
        ImageDto image = eventImageService.getWallpaperByOwnerId(event.getId());
        return eventMapper.eventToShortDto(event, translationShortDto, image);
    }

    private SearchedEventDto translationToSearchedEventDto(EventTranslation eventTranslation) {
        Event event = eventTranslation.getEntity();
        return eventMapper.translationToSearchedEventDto(event, eventTranslation, eventImageService.getWallpaperByOwnerId(event.getId()));
    }



}
