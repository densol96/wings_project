package lv.wings.service.impl;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import lombok.NonNull;

import lv.wings.dto.response.color.ColorDto;
import lv.wings.mapper.ColorMapper;
import lv.wings.model.entity.Color;
import lv.wings.model.translation.ColorTranslation;
import lv.wings.repo.ColorRepository;
import lv.wings.service.AbstractTranslatableCRUDService;
import lv.wings.service.ColorService;
import lv.wings.service.LocaleService;

@Service
public class ColorServiceImpl extends AbstractTranslatableCRUDService<Color, ColorTranslation, Integer> implements ColorService {

    private final ColorMapper colorMapper;
    private final ColorRepository colorRepo;

    public ColorServiceImpl(ColorRepository colorRepo, LocaleService localeService, ColorMapper colorMapper) {
        super(colorRepo, "Color", "entity.color", localeService);
        this.colorMapper = colorMapper;
        this.colorRepo = colorRepo;
    }

    @Override
    public ColorDto mapColorToDto(@NonNull Color color) {
        return colorMapper.mapToDto(color, getRightTranslation(color, ColorTranslation.class));
    }

    @Override
    public List<ColorDto> getAllColors() {
        return findAll().stream().map(this::mapColorToDto).toList();
    }

    @Override
    public List<Color> getAllColorsByIds(List<Integer> ids) {
        return colorRepo.findAllByIdIn(ids);
    }
}
