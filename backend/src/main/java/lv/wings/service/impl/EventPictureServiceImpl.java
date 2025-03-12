package lv.wings.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import lv.wings.dto.response.ImageDto;
import lv.wings.model.entity.EventPicture;
import lv.wings.model.translation.EventPictureTranslation;
import lv.wings.repo.EventPictureRepository;
import lv.wings.service.AbstractTranslatableCRUDService;
import lv.wings.service.EventPictureService;
import lv.wings.service.LocaleService;

@Service
public class EventPictureServiceImpl extends AbstractTranslatableCRUDService<EventPicture, EventPictureTranslation, Integer> implements EventPictureService {

	private final EventPictureRepository eventPictureRepo;

	public EventPictureServiceImpl(EventPictureRepository eventPictureRepo, LocaleService localeService) {
		super(eventPictureRepo, "EventPicture", "entity.event-picture", localeService);
		this.eventPictureRepo = eventPictureRepo;
	}

	@Override
	public List<ImageDto> getPicturesAsDtoPerEventId(Integer id) {
		return eventPictureRepo.findAllByEventId(id).stream().map(this::imageToDto).toList();
	}

	@Override
	public ImageDto getEventWallpaperById(Integer id) {
		List<ImageDto> pictures = getPicturesAsDtoPerEventId(id);
		return pictures.isEmpty() ? null : pictures.get(0);
	}

	private ImageDto imageToDto(EventPicture image) {
		return ImageDto.builder()
				.src(image.getSrc())
				.alt(getRightTranslation(image, EventPictureTranslation.class).getAlt())
				.build();
	}
}
