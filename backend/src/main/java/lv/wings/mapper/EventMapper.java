package lv.wings.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import lv.wings.dto.response.ImageDto;
import lv.wings.dto.response.event.EventTranslationDto;
import lv.wings.dto.response.event.EventTranslationShortDto;
import lv.wings.dto.response.event.ShortEventDto;
import lv.wings.dto.response.event.SingleEventDto;
import lv.wings.model.entity.Event;
import lv.wings.model.translation.EventTranslation;

@Mapper(componentModel = "spring", uses = { ImageMapper.class })
public interface EventMapper {

    EventTranslationShortDto eventTranslationToShortDto(EventTranslation translation);

    EventTranslationDto eventTranslationToDto(EventTranslation translation);

    ShortEventDto eventToShortDto(Event event, EventTranslationShortDto translation, ImageDto image);

    @Mapping(target = "translation", source = "translation")
    SingleEventDto eventToFullDto(Event event, EventTranslationDto translation);

}