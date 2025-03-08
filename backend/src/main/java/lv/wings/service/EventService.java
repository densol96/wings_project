package lv.wings.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import lv.wings.dto.response.event.PublicEventDto;
import lv.wings.model.Event;

public interface EventService extends CRUDService<Event, Integer> {
    Page<PublicEventDto> getEvents(Pageable pageable);

    PublicEventDto getEvent(Integer id);
}
