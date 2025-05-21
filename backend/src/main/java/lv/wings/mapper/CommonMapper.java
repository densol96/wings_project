package lv.wings.mapper;

import org.mapstruct.Mapper;
import lv.wings.dto.response.admin.common.TitleLocalableDto;
import lv.wings.model.interfaces.HasTitle;

@Mapper(componentModel = "spring")
public interface CommonMapper {

    TitleLocalableDto toTitleWithLocale(HasTitle withTitle);
}
