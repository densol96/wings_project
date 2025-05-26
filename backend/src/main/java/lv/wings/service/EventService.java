package lv.wings.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import lv.wings.dto.response.event.SearchedEventDto;
import lv.wings.dto.response.event.ShortEventDto;
import lv.wings.dto.response.event.SingleEventDto;
import lv.wings.enums.LocaleCode;
import lv.wings.model.entity.Event;
import lv.wings.model.translation.EventTranslation;
import lv.wings.dto.response.admin.events.EventAdminDto;
import lv.wings.dto.response.admin.events.ExistingEventDto;
import lv.wings.dto.request.admin.events.NewEventDto;
import lv.wings.dto.response.BasicMessageDto;

public interface EventService extends CRUDService<Event, Integer> {
    Page<ShortEventDto> getPublicEvents(Pageable pageable);

    SingleEventDto getPublicEvent(Integer id);

    List<SearchedEventDto> getSearchedEvents(String q);

    EventTranslation getSelectedTranslation(Event event, LocaleCode locale);

    Page<EventAdminDto> getAllEventsForAdmin(String q, Pageable pageable, LocalDate start, LocalDate end);

    ExistingEventDto getExistingProductForAdmin(Integer id);

    BasicMessageDto deleteEvent(Integer id);

    BasicMessageDto createEvent(NewEventDto dto);

    BasicMessageDto updateEvent(NewEventDto dto, Integer id);
}
