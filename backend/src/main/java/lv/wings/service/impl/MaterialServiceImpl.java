package lv.wings.service.impl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import io.micrometer.common.lang.NonNull;
import lv.wings.dto.response.product.MaterialDto;
import lv.wings.mapper.MaterialMapper;
import lv.wings.model.entity.Material;
import lv.wings.model.translation.MaterialTranslation;
import lv.wings.service.AbstractTranslatableCRUDService;
import lv.wings.service.LocaleService;
import lv.wings.service.MaterialService;

@Service
public class MaterialServiceImpl extends AbstractTranslatableCRUDService<Material, MaterialTranslation, Integer> implements MaterialService {

    private final MaterialMapper materialMapper;

    public MaterialServiceImpl(JpaRepository<Material, Integer> repository, LocaleService localeService, MaterialMapper materialMapper) {
        super(repository, "Material", "entity.material", localeService);
        this.materialMapper = materialMapper;
    }

    @Override
    public MaterialDto mapToDto(@NonNull Material material) {
        return materialMapper.toDto(material, getRightTranslation(material, MaterialTranslation.class));
    }

}
