package lv.wings.service;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import lv.wings.dto.response.BasicMessageDto;
import lv.wings.dto.response.ImageDto;
import lv.wings.dto.response.admin.images.AdminImageDto;
import lv.wings.model.interfaces.HasImages;
import lv.wings.model.interfaces.Imageable;

public interface ImageService<T extends Imageable, O extends HasImages<T>, ID> extends CRUDService<T, ID> {
    ImageDto getWallpaperByOwnerId(Integer id);

    List<ImageDto> getImagesAsDtoPerOwnerId(Integer id);

    ImageDto mapImageToDto(T image);

    List<ImageDto> getTwoImagesForCover(List<ImageDto> images);

    List<ImageDto> getTwoImagesForCover(Integer id);

    List<T> proccessImagesAndUpload(O newProduct, List<MultipartFile> dtoImages);

    void clearImagesUp(List<T> images, MultipartFile faultyImage);

    BasicMessageDto addMoreImages(Integer id, List<MultipartFile> images);

    BasicMessageDto deleteImage(ID imageId);

    List<AdminImageDto> getAdminImages(Integer ownerId);
}
