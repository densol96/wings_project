package lv.wings.mapper;

import org.mapstruct.Mapper;

import lv.wings.dto.response.ImageDto;
import lv.wings.model.interfaces.Imagable;

@Mapper(componentModel = "spring")
public interface ImageMapper {
    ImageDto imageToDto(Imagable image);
}
