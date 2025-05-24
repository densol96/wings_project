package lv.wings.service.shared;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import lombok.extern.slf4j.Slf4j;
import lv.wings.enums.LocaleCode;
import lv.wings.model.interfaces.HasImages;
import lv.wings.model.interfaces.Imageable;
import lv.wings.service.S3Service;
import lv.wings.util.mapping.EntityTitleProvider;
import lv.wings.util.mapping.ImageFactory;
import lv.wings.util.mapping.ImageTranslationFactory;

@Slf4j
@Component
public class GenericImageProcessor {

    private final S3Service awsS3service;

    public GenericImageProcessor(S3Service awsS3service) {
        this.awsS3service = awsS3service;
    }

    public <T extends HasImages<I>, I extends Imageable, IT> List<I> processImagesAndUpload(
            T parentEntity,
            Integer position,
            List<MultipartFile> dtoImages,
            String s3Folder,
            ImageFactory<T, I> imageFactory,
            BiConsumer<I, List<IT>> imageTranslationSetter,
            ImageTranslationFactory<I, IT> translationFactory,
            EntityTitleProvider<T> titleProvider) {

        List<I> images = new ArrayList<>();

        int index = position + 1;
        for (MultipartFile file : dtoImages) {

            try {
                String url = awsS3service.uploadFile(file, s3Folder);
                I image = imageFactory.create(parentEntity, url);

                List<IT> translations = List.of(
                        translationFactory.create(LocaleCode.LV, image,
                                String.format("Produkta %s attēls Nr. %d",
                                        titleProvider.getTitle(parentEntity, LocaleCode.LV),
                                        index)),
                        translationFactory.create(LocaleCode.EN, image,
                                String.format("Product %s image Nr. %d",
                                        titleProvider.getTitle(parentEntity, LocaleCode.EN),
                                        index)));

                imageTranslationSetter.accept(image, translations);
                images.add(image);
                index++;
            } catch (Exception e) {
                log.error(e.getMessage());
                clearImagesUp(images, file); // реализуй универсально
                throw new RuntimeException("Failed to upload image: " + file.getOriginalFilename(), e);
            }
        }
        return images;
    }

    public <I extends Imageable> void clearImagesUp(List<I> images, MultipartFile faultyImage) {
        try {
            images.forEach(alreadyUploaded -> {
                awsS3service.deleteFile(alreadyUploaded.getSrc());
            });
        } catch (Exception ex) {
            log.error(ex.getMessage());
            if (faultyImage == null) {
                throw new RuntimeException("Failed to save to DB links to the uploaded images => required image clean up",
                        ex);
            } else {
                throw new RuntimeException("Failed to upload image: " + faultyImage.getOriginalFilename() + " and failed to clear already uploaded images",
                        ex);
            }

        }
    }

    public <I extends Imageable> void deleteImage(I image) {
        try {
            awsS3service.deleteFile(image.getSrc());
        } catch (Exception e) {
            String message = "Failed to delete image from S3: " + image.getSrc();
            log.error(message, e);
            throw new RuntimeException(message);
            // will triogger a procedular exception handling aka service currently unavailable but add a proper log on the server
        }
    }
}
