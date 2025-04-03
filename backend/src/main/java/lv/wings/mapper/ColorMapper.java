package lv.wings.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import lv.wings.dto.response.color.ColorDto;
import lv.wings.model.entity.Color;
import lv.wings.model.translation.ColorTranslation;

@Mapper(componentModel = "spring")
public interface ColorMapper {

    @Mapping(target = "id", source = "color.id")
    @Mapping(target = "name", source = "translation.name")
    ColorDto mapToDto(Color color, ColorTranslation translation);
}
