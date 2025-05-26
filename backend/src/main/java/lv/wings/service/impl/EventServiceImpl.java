package lv.wings.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lv.wings.config.security.UserSecurityService;
import lv.wings.dto.request.admin.events.NewEventDto;
import lv.wings.dto.request.admin.events.NewEventTranslationDto;

import lv.wings.dto.response.BasicMessageDto;
import lv.wings.dto.response.ImageDto;
import lv.wings.dto.response.admin.events.EventAdminDto;
import lv.wings.dto.response.admin.events.ExistingEventDto;
import lv.wings.dto.response.event.EventTranslationDto;
import lv.wings.dto.response.event.SearchedEventDto;
import lv.wings.dto.response.event.ShortEventDto;
import lv.wings.dto.response.event.ShortEventTranslationDto;
import lv.wings.dto.response.event.SingleEventDto;
import lv.wings.enums.LocaleCode;
import lv.wings.exception.validation.ConstraintValidationException;
import lv.wings.exception.validation.NestedValidationException;
import lv.wings.mapper.EventMapper;
import lv.wings.model.entity.Event;
import lv.wings.model.entity.EventImage;
import lv.wings.model.translation.EventTranslation;

import lv.wings.repo.EventRepository;
import lv.wings.repo.EventTranslationRepository;
import lv.wings.service.AbstractTranslatableCRUDService;
import lv.wings.service.EventService;
import lv.wings.service.ImageService;
import lv.wings.service.LocaleService;
import lv.wings.service.TranslationService;
import lv.wings.util.CustomValidator;

@Service
public class EventServiceImpl extends AbstractTranslatableCRUDService<Event, EventTranslation, Integer>
        implements EventService {

    private final EventRepository eventRepo;
    private final EventTranslationRepository eventTranslationRepository;
    private final EventMapper eventMapper;
    private final ImageService<EventImage, Event, Integer> eventImageService;
    private final Validator validator;
    private final TranslationService translationService;
    private final UserSecurityService userSecurityService;

    @PersistenceContext
    private EntityManager entityManager;

    public EventServiceImpl(
            EventRepository eventRepo,
            EventRepository eventRepository,
            EventTranslationRepository eventTranslationRepository,
            EventMapper eventMapper,
            ImageService<EventImage, Event, Integer> eventImageService,
            LocaleService localeService,
            Validator validator,
            TranslationService translationService,
            UserSecurityService userSecurityService) {
        super(eventRepository, "Event", "entity.event", localeService);
        this.eventRepo = eventRepo;
        this.eventTranslationRepository = eventTranslationRepository;
        this.eventMapper = eventMapper;
        this.eventImageService = eventImageService;
        this.validator = validator;
        this.translationService = translationService;
        this.userSecurityService = userSecurityService;
    }

    @Override
    public Page<ShortEventDto> getPublicEvents(Pageable pageable) {
        return findAll(pageable).map(this::eventToShortPublicDto);
    }

    @Override
    public SingleEventDto getPublicEvent(Integer id) {
        Event event = findById(id);
        EventTranslationDto translation = eventMapper
                .eventTranslationToDto(getRightTranslation(event, EventTranslation.class));
        List<ImageDto> images = eventImageService.getImagesAsDtoPerOwnerId(id);
        return eventMapper.eventToFullDto(event, translation, images);
    }

    @Override
    public List<SearchedEventDto> getSearchedEvents(String q) {
        if (q.isBlank())
            return new ArrayList<>();

        return eventTranslationRepository
                .findTop1000ByTitleContainingIgnoreCaseAndLocaleEquals(q, localeService.getCurrentLocaleCode()).stream()
                .map(this::translationToSearchedEventDto)
                .toList();
    }

    @Override
    public EventTranslation getSelectedTranslation(Event parentProduct, LocaleCode locale) {
        return getRightTranslationForSelectedLocale(parentProduct, EventTranslation.class, locale);
    }

    @Override
    public Page<EventAdminDto> getAllEventsForAdmin(String q, Pageable pageable, LocalDate start, LocalDate end) {
        return eventRepo.findAll((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (q != null && !q.isBlank()) {
                String likePattern = "%" + q.toLowerCase() + "%";
                predicates.add(cb.or(
                        cb.like(cb.lower(root.get("title")), likePattern),
                        cb.like(cb.lower(root.get("description")), likePattern)));
            }

            if (start != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("startDate"), start));
            }

            if (end != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("endDate"), end));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        }, pageable).map(eventMapper::toAdminDto);
    }

    @Override
    public ExistingEventDto getExistingProductForAdmin(Integer id) {
        return eventMapper.toExistingDto(findById(id));
    }

    @Override
    @Transactional
    public BasicMessageDto deleteEvent(Integer id) {
        Event eventForDelete = findById(id);
        List<EventImage> eventImages = eventForDelete.getImages();
        eventImages.stream().forEach(image -> eventImageService.deleteImage(image.getId()));
        eventRepo.delete(eventForDelete);
        return new BasicMessageDto("Notikums tika veiksmīgi dzēsts");
    }

    @Override
    @Transactional
    public BasicMessageDto createEvent(NewEventDto dto) {
        validateNewEventDto(dto, null);
        List<NewEventTranslationDto> providedTranslations = dto.getTranslations();
        NewEventTranslationDto defaultTranslation = localeService.validateDefaultLocaleIsPresent(providedTranslations);
        localeService.validateOneTranslationPerEachLocale(providedTranslations);
        boolean isManualTranslation = localeService.validateRequiredTranslationsPresentIfManual(dto);

        Event newProduct = new Event(dto.getStartDate(), dto.getEndDate());
        attachTranslations(newProduct, dto, isManualTranslation, defaultTranslation);

        List<EventImage> images = attachImages(newProduct, dto.getImages());

        try {
            persist(newProduct);
        } catch (Exception e) {
            if (images != null) {
                eventImageService.clearImagesUp(images, null);
            }
            throw new RuntimeException(e); // wil trigger a procedural error handling but user will see the the 500 error
        }
        return new BasicMessageDto("Jauns notikums tika pievienots.");
    }

    @Override
    @Transactional
    public BasicMessageDto updateEvent(NewEventDto dto, Integer id) {
        Event existingEvent = findById(id);
        validateNewEventDto(dto, existingEvent);
        List<NewEventTranslationDto> providedTranslations = dto.getTranslations();
        NewEventTranslationDto defaultTranslation = localeService.validateDefaultLocaleIsPresent(providedTranslations);
        localeService.validateOneTranslationPerEachLocale(providedTranslations);
        boolean isManualTranslation = localeService.validateRequiredTranslationsPresentIfManual(dto);

        existingEvent.setStartDate(dto.getStartDate());
        existingEvent.setEndDate(dto.getEndDate());
        // Audit are working, however they will not record the activity if it was only the translationd fields that were updated
        existingEvent.setLastModifiedAt(LocalDateTime.now());
        existingEvent.setLastModifiedBy(userSecurityService.getCurrentUser());

        attachTranslations(existingEvent, dto, isManualTranslation, defaultTranslation);

        persist(existingEvent);
        return new BasicMessageDto("Notikums tika atjaunots.");
    }

    private List<EventImage> attachImages(Event event, List<MultipartFile> imagesDto) {
        if (imagesDto == null || imagesDto.isEmpty())
            return Collections.emptyList();

        List<EventImage> images = eventImageService.proccessImagesAndUpload(event, imagesDto);
        event.getImages().addAll(images);
        return images;
    }

    private void attachTranslations(
            Event newEvent,
            NewEventDto dto,
            boolean isManualTranslation,
            NewEventTranslationDto defaultTranslation) {
        List<EventTranslation> translations = new ArrayList<>();
        if (isManualTranslation) {
            dto.getTranslations().forEach(tr -> {
                translations.add(EventTranslation.builder()
                        .locale(tr.getLocale())
                        .title(tr.getTitle())
                        .description(tr.getDescription())
                        .location(tr.getLocation())
                        .build());
            });
        } else {
            String title = defaultTranslation.getTitle();
            String description = defaultTranslation.getDescription();
            String location = defaultTranslation.getLocation();

            translations.add(EventTranslation.builder()
                    .locale(defaultTranslation.getLocale())
                    .title(title)
                    .description(description)
                    .location(location)
                    .event(newEvent)
                    .build());

            translations.add(EventTranslation.builder()
                    .locale(LocaleCode.EN)
                    .title(title)
                    .description(description != null ? translationService.translateToEnglish(description) : null)
                    .location(location != null ? translationService.translateToEnglish(location) : null)
                    .event(newEvent)
                    .build());
        }

        newEvent.getNarrowTranslations().clear();
        entityManager.flush();
        // Apparently, with orphanRemoval INSERTS happen before DELETE, therefore I need to flush the context here to avoid a unqinuess constraint
        newEvent.getNarrowTranslations().addAll(translations);
    }

    private void validateNewEventDto(NewEventDto dto, Event forUpdate) {
        Set<ConstraintViolation<NewEventDto>> violations = validator.validate(dto);
        if (!violations.isEmpty()) {
            throw new ConstraintValidationException((Set<ConstraintViolation<?>>) (Set<?>) violations);
        }

        Map<String, Object> errors = new HashMap<>();
        CustomValidator.validateTitleUniqueness(dto, errors,
                (string, locale) -> eventTranslationRepository.existsByTitleAndLocale(string, locale), forUpdate);

        if (!errors.isEmpty())
            throw new NestedValidationException(errors);
    }

    private ShortEventDto eventToShortPublicDto(Event event) {
        EventTranslation translation = getRightTranslation(event, EventTranslation.class);
        ShortEventTranslationDto translationShortDto = eventMapper.eventTranslationToShortDto(translation);
        ImageDto image = eventImageService.getWallpaperByOwnerId(event.getId());
        return eventMapper.eventToShortDto(event, translationShortDto, image);
    }

    private SearchedEventDto translationToSearchedEventDto(EventTranslation eventTranslation) {
        Event event = eventTranslation.getEntity();
        return eventMapper.translationToSearchedEventDto(event, eventTranslation,
                eventImageService.getWallpaperByOwnerId(event.getId()));
    }

}
