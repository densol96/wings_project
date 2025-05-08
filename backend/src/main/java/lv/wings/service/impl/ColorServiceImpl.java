package lv.wings.service.impl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import lombok.NonNull;

import lv.wings.dto.response.color.ColorDto;
import lv.wings.mapper.ColorMapper;
import lv.wings.model.entity.Color;
import lv.wings.model.translation.ColorTranslation;
import lv.wings.service.AbstractTranslatableCRUDService;
import lv.wings.service.ColorService;
import lv.wings.service.LocaleService;

@Service
public class ColorServiceImpl extends AbstractTranslatableCRUDService<Color, ColorTranslation, Integer> implements ColorService {

    private final ColorMapper colorMapper;

    public ColorServiceImpl(JpaRepository<Color, Integer> repository, LocaleService localeService, ColorMapper colorMapper) {
        super(repository, "Color", "entity.color", localeService);
        this.colorMapper = colorMapper;
    }

    @Override
    public ColorDto mapColorToDto(@NonNull Color color) {
        ColorDto dto = colorMapper.mapToDto(color, getRightTranslation(color, ColorTranslation.class));
        System.out.println("=== RESULTED DTO ===");
        System.out.println(dto.getId());
        System.out.println(dto.getName());
        System.out.println("===================");
        return dto;
    }

}
