package lv.wings.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import lv.wings.dto.response.ImageDto;
import lv.wings.dto.response.event.EventTranslationDto;
import lv.wings.dto.response.event.SearchedEventDto;
import lv.wings.dto.response.event.ShortEventDto;
import lv.wings.dto.response.event.ShortEventTranslationDto;
import lv.wings.dto.response.event.SingleEventDto;
import lv.wings.model.entity.Event;
import lv.wings.model.translation.EventTranslation;

@Mapper(componentModel = "spring")
public interface EventMapper {

    ShortEventTranslationDto eventTranslationToShortDto(EventTranslation translation);

    EventTranslationDto eventTranslationToDto(EventTranslation translation);

    ShortEventDto eventToShortDto(Event event, ShortEventTranslationDto translation, ImageDto imageDto);

    SingleEventDto eventToFullDto(Event event, EventTranslationDto translationDto, List<ImageDto> imageDtos, String categoryName);

    @Mapping(target = "id", source = "event.id")
    SearchedEventDto translationToSearchedEventDto(Event event, EventTranslation translation, ImageDto imageDto);

}
