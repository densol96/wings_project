package lv.wings.service;

import java.util.List;
import java.util.regex.Pattern;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;
import lombok.NonNull;
import lv.wings.dto.response.BasicMessageDto;
import lv.wings.dto.response.ImageDto;
import lv.wings.enums.LocaleCode;
import lv.wings.exception.validation.InvalidProceduralException;
import lv.wings.model.base.ImageLocalableEntity;
import lv.wings.model.base.ImageableEntity;
import lv.wings.model.interfaces.HasImages;
import lv.wings.repo.base.ImageRepository;
import lv.wings.service.shared.GenericImageProcessor;
import lv.wings.util.CustomValidator;

public abstract class AbstractImageService<T extends ImageableEntity<L, O>, O extends HasImages<T>, L extends ImageLocalableEntity<T>, ID>
        extends AbstractTranslatableCRUDService<T, L, ID>
        implements ImageService<T, O, ID> {

    private final ImageRepository<T, ID> repository;
    private final Class<L> expectedTranslationClass;
    private final GenericImageProcessor imageProcessor;
    private final String folderName;

    private static final Pattern ENTITY_KEY_PATTERN = Pattern.compile("^entity\\.(\\w+)\\-image$");

    protected AbstractImageService(
            ImageRepository<T, ID> repository,
            Class<L> expectedTranslationClass,
            String entityName,
            String entityNameKey,
            LocaleService localeService,
            GenericImageProcessor imageProcessor,
            String folderName) {
        super(repository, entityName, entityNameKey, localeService);

        /**
         * I am using a specific format later to assemble an associated owner id nameCode (to be used in a global exception handler for i18n)
         * so it is better to add a check here to catch any runtime exception early..
         */
        if (!ENTITY_KEY_PATTERN.matcher(entityNameKey).matches()) {
            throw new InvalidProceduralException("Invalid entityNameKey format: " + entityNameKey);
        }

        this.repository = repository;
        this.expectedTranslationClass = expectedTranslationClass;
        this.folderName = folderName;
        this.imageProcessor = imageProcessor;
    }

    @Override
    public List<ImageDto> getImagesAsDtoPerOwnerId(Integer id) {
        CustomValidator.isValidId(getOwnerIdCode(), id);
        return repository.findAllByOwnerId(id).stream().map(this::mapImageToDto).toList();
    }

    @Override
    public ImageDto getWallpaperByOwnerId(Integer id) {
        List<ImageDto> listWithAnImage = getImagesAsDtoPerOwnerIdWithLimit(id, 1);
        return listWithAnImage.isEmpty() ? null : listWithAnImage.get(0);
    }

    @Override
    public List<ImageDto> getTwoImagesForCover(Integer id) {
        return getImagesAsDtoPerOwnerIdWithLimit(id, 2);
    }

    @Override
    public List<ImageDto> getTwoImagesForCover(@NonNull List<ImageDto> images) {
        return images.subList(0, Math.min(images.size(), 2));
    }

    @Override
    public ImageDto mapImageToDto(@NonNull T image) {
        return ImageDto.builder()
                .src(image.getSrc())
                .alt(getRightTranslation(image, expectedTranslationClass).getAlt())
                .build();
    }

    protected abstract T getImageInstance(O parentEntity, String url);

    protected abstract L createImageTranslation(LocaleCode locale, T image, String title);

    protected abstract String getTitleFromParent(O parentEntity, LocaleCode locale);

    @Override
    public List<T> proccessImagesAndUpload(O newOwner, List<MultipartFile> dtoImages) {
        return imageProcessor.processImagesAndUpload(
                newOwner,
                repository.findMaxPositionByOwnerId(newOwner.getId()),
                dtoImages,
                folderName,
                (parentEntity, url) -> getImageInstance(parentEntity, url),
                (image, translations) -> image.setTranslations(translations),
                (locale, image, title) -> createImageTranslation(locale, image, title),
                (parentEntity, locale) -> getTitleFromParent(parentEntity, locale));
    }

    protected abstract O getOwnerById(Integer id);

    @Override
    public BasicMessageDto addMoreImages(Integer id, List<MultipartFile> images) {
        proccessImagesAndUpload(getOwnerById(id), images);
        return new BasicMessageDto("Jauni attēli tika veiksmīgi pievienoti.");
    }

    @Override
    public void clearImagesUp(@NonNull List<T> images, MultipartFile faultyImage) {
        imageProcessor.clearImagesUp(images, faultyImage);
    }

    @Override
    public BasicMessageDto deleteImage(ID imageId) {
        T image = findById(imageId);
        imageProcessor.deleteImage(image);
        repository.delete(image);
        return new BasicMessageDto("Attēls tika veiksmīgi izdzēsts.");
    }

    private List<ImageDto> getImagesAsDtoPerOwnerIdWithLimit(Integer id, Integer limit) {
        CustomValidator.isValidId(getOwnerIdCode(), id);
        if (limit <= 0)
            return List.of();
        return repository.findAllByOwnerId(id, PageRequest.of(0, limit)).stream().map(this::mapImageToDto).toList();
    }

    private String getOwnerIdCode() {
        // entity.product-image and need product.id OR entity.event-image and need event.id
        return entityNameKey.split("-")[0].split("\\.")[1] + ".id";
    }
}
