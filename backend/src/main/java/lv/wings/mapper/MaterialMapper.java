package lv.wings.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import lv.wings.dto.response.product.MaterialDto;
import lv.wings.model.entity.Material;
import lv.wings.model.translation.MaterialTranslation;

@Mapper(componentModel = "spring")
public interface MaterialMapper {

    @Mapping(target = "id", source = "material.id")
    MaterialDto toDto(Material material, MaterialTranslation translation);
}
