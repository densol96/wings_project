package lv.wings.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import lv.wings.dto.response.admin.security_events.SecurityEventDto;
import lv.wings.model.security.SecurityEvent;

@Mapper(componentModel = "spring")
public interface SecurityEventMapper {

    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "eventType", expression = "java(event.getEventType().getLabel(\"lv\"))")
    SecurityEventDto toDto(SecurityEvent event);
}
