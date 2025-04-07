package lv.wings.service;

import java.util.List;
import lv.wings.dto.response.ImageDto;
import lv.wings.model.interfaces.Imageable;

public interface ImageService<T extends Imageable, ID> extends CRUDService<T, ID> {
    ImageDto getWallpaperByOwnerId(Integer id);

    List<ImageDto> getImagesAsDtoPerOwnerId(Integer id);

    ImageDto mapImageToDto(T image);

    List<ImageDto> getTwoImagesForCover(List<ImageDto> images);

    List<ImageDto> getTwoImagesForCover(Integer id);
}
