package lv.wings.mapper;

import org.mapstruct.Mapper;

import lv.wings.dto.response.ImageDto;
import lv.wings.dto.response.event.EventTranslationShortDto;
import lv.wings.dto.response.event.ShortEventDto;
import lv.wings.model.entity.Event;
import lv.wings.model.translation.EventTranslation;

@Mapper(componentModel = "spring", uses = { ImageMapper.class })
public interface EventMapper {

    ShortEventDto eventToShortDto(Event event, EventTranslationShortDto translation, ImageDto image);

    EventTranslationShortDto eventTranslationToShortDto(EventTranslation translation);

}