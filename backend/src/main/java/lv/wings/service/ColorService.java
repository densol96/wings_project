package lv.wings.service;

import lv.wings.dto.response.color.ColorDto;
import lv.wings.model.entity.Color;

public interface ColorService {
    ColorDto mapColorToDto(Color color);
}
