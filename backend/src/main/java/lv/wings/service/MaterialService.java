package lv.wings.service;

import java.util.List;
import lv.wings.dto.response.product.MaterialDto;
import lv.wings.enums.LocaleCode;
import lv.wings.model.entity.Material;
import lv.wings.model.translation.MaterialTranslation;

public interface MaterialService extends CRUDService<Material, Integer> {
    MaterialDto mapToDto(Material material);

    List<MaterialDto> getAllMaterials();

    MaterialTranslation getSelectedTranslation(Material product, LocaleCode localeCode);
}
