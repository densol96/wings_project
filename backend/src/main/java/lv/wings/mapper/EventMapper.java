package lv.wings.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import lv.wings.dto.response.ImageDto;
import lv.wings.dto.response.admin.common.TitleLocalableDto;
import lv.wings.dto.response.admin.events.EventAdminDto;
import lv.wings.dto.response.admin.events.ExistingEventDto;
import lv.wings.dto.response.admin.events.ExistingEventTranslationDto;
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

    SingleEventDto eventToFullDto(Event event, EventTranslationDto translationDto, List<ImageDto> imageDtos);

    @Mapping(target = "id", source = "event.id")
    SearchedEventDto translationToSearchedEventDto(Event event, EventTranslation translation, ImageDto imageDto);

    @Mapping(target = "translations", source = "narrowTranslations")
    EventAdminDto toAdminDto(Event event);

    @Mapping(target = "translations", source = "narrowTranslations")
    ExistingEventDto toExistingDto(Event event);

    ExistingEventTranslationDto toExistingEventDto(EventTranslation translation);

    TitleLocalableDto toTitleLocalableDto(EventTranslation translation);
}
