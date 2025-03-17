package lv.wings.service;

import java.util.List;
import org.apache.poi.ss.formula.functions.T;

import lv.wings.dto.response.ImageDto;
import lv.wings.model.base.ImageLocalableEntity;
import lv.wings.model.base.ImageableEntity;
import lv.wings.model.interfaces.HasImages;
import lv.wings.repo.ImageRepository;

public abstract class AbstractImageService<T extends ImageableEntity<L, O>, O extends HasImages<T>, L extends ImageLocalableEntity<T>, ID>
        extends AbstractTranslatableCRUDService<T, L, ID> implements ImageService<T, ID> {

    private final ImageRepository<T, ID> repository;
    private final Class<L> expectedTranslationClass;

    protected AbstractImageService(
            ImageRepository<T, ID> repository,
            Class<L> expectedTranslationClass,
            String entityName,
            String entityNameKey,
            LocaleService localeService) {
        super(repository, entityName, entityNameKey, localeService);
        this.repository = repository;
        this.expectedTranslationClass = expectedTranslationClass;
    }


    @Override
    public List<ImageDto> getImagesAsDtoPerOwnerId(Integer id) {
        return repository.findAllByOwnerId(id).stream().map(this::mapImageToDto).toList();
    }


    @Override
    public ImageDto getWallpaperByOwnerId(Integer id) {
        List<ImageDto> pictures = getImagesAsDtoPerOwnerId(id);
        return pictures.isEmpty() ? null : pictures.get(0);
    }

    @Override
    public ImageDto mapImageToDto(T image) {
        return ImageDto.builder()
                .src(image.getSrc())
                .alt(getRightTranslation(image, expectedTranslationClass).getAlt())
                .build();
    }
}
