package lv.wings.service;

import java.util.List;
import lv.wings.dto.response.color.ColorDto;
import lv.wings.model.entity.Color;

public interface ColorService extends CRUDService<Color, Integer> {
    ColorDto mapColorToDto(Color color);

    List<ColorDto> getAllColors();

    List<Color> getAllColorsByIds(List<Integer> ids);
}
