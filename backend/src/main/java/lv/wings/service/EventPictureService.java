package lv.wings.service;

import java.util.List;
import lv.wings.dto.response.ImageDto;
import lv.wings.model.entity.EventPicture;

public interface EventPictureService extends CRUDService<EventPicture, Integer> {
    ImageDto getEventWallpaperById(Integer id);

    List<ImageDto> getPicturesAsDtoPerEventId(Integer id);
}
