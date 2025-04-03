package lv.wings.service;

import lv.wings.dto.response.product.MaterialDto;
import lv.wings.model.entity.Material;

public interface MaterialService {
    MaterialDto mapToDto(Material material);
}
