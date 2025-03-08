package lv.wings.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import lv.wings.dto.response.event.EventTranslationDto;
import lv.wings.dto.response.event.PublicEventDto;
import lv.wings.model.Event;
import lv.wings.model.translation.EventTranslation;

@Mapper(componentModel = "spring")
public interface EventMapper {

    EventMapper INSTANCE = Mappers.getMapper(EventMapper.class);

    @Mapping(target = "imageUrl", expression = "java(event.getEventPictures() != null && !event.getEventPictures().isEmpty() ? event.getEventPictures().get(0).getImageUrl() : null)")
    @Mapping(target = "category", expression = "java(event.getCategory() != null ? event.getCategory().getTitle() : null)")
    @Mapping(target = "translation", source = "translation")
    PublicEventDto eventToPublicDto(Event product, EventTranslationDto translation);

    EventTranslationDto eventTranslationToDto(EventTranslation translation);
}