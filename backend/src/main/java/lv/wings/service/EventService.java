package lv.wings.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import lv.wings.dto.response.event.ShortEventDto;
import lv.wings.model.entity.Event;

public interface EventService extends CRUDService<Event, Integer> {
    Page<ShortEventDto> getEvents(Pageable pageable);

    ShortEventDto getEvent(Integer id);
}
