package lv.wings.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lv.wings.dto.response.ImageDto;
import lv.wings.mapper.ImageMapper;
import lv.wings.model.entity.EventPicture;
import lv.wings.repo.IEventPictureRepo;
import lv.wings.service.ICRUDService;

@Service
@RequiredArgsConstructor
public class EventPictureServiceImpl implements ICRUDService<EventPicture> {

	private final IEventPictureRepo eventPictureRepo;
	private final ImageMapper imageMapper;

	@Override
	@Cacheable("EventPictures")
	public ArrayList<EventPicture> retrieveAll() {
		if (eventPictureRepo.count() == 0)
			throw new RuntimeException("There are no event pictures in the database");

		return (ArrayList<EventPicture>) eventPictureRepo.findAll();
	}

	@Override
	@Cacheable(value = "EventPictures", key = "#pageable.pageNumber + '-' + #pageable.pageSize + '-' + #pageable.sort")
	public Page<EventPicture> retrieveAll(Integer page, Integer size, String sortBy, String sortDirection) {
		if (eventPictureRepo.count() == 0)
			throw new RuntimeException("There are no event pictures in the database");
		Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDirection), sortBy));
		return (Page<EventPicture>) eventPictureRepo.findAll(pageable);
	}

	@Override
	@Cacheable(value = "EventPictures", key = "#id")
	public EventPicture retrieveById(Integer id) {
		if (id < 1)
			throw new RuntimeException("Invalid ID");

		if (eventPictureRepo.existsById(id)) {
			return eventPictureRepo.findById(id).get();
		} else {
			throw new RuntimeException("Event picture with the id: (" + id + ") does not exist!");
		}
	}

	@Override
	@CacheEvict(value = "EventPictures", allEntries = true)
	public void deleteById(Integer id) {
		EventPicture eventPicture = retrieveById(id);
		if (eventPicture == null)
			throw new RuntimeException("Event picture with the id: (" + id + ") does not exist!");

		// System.out.prIntegerln(eventPicture.getReferenceToPicture());
		eventPictureRepo.delete(eventPicture);
	}

	@Override
	@CacheEvict(value = "EventPictures", allEntries = true)
	public void create(EventPicture eventPicture) {
		EventPicture existedEventPicture = eventPictureRepo.findBySrc(eventPicture.getSrc());

		if (existedEventPicture != null)
			throw new RuntimeException("Event picture with url: " + eventPicture.getSrc() + " already exists");

		eventPictureRepo.save(eventPicture);

	}

	@Override
	@CacheEvict(value = "EventPictures", allEntries = true)
	@CachePut(value = "EventPictures", key = "#id")
	public void update(Integer id, EventPicture eventPicture) {
		EventPicture foundEventPicture = retrieveById(id);

		if (foundEventPicture == null)
			throw new RuntimeException("Event picture with the id: (" + id + ") does not exist!");

		foundEventPicture.setSrc(eventPicture.getSrc());
		foundEventPicture.setAlt(eventPicture.getAlt());

		eventPictureRepo.save(foundEventPicture);

	}

	public List<ImageDto> getPicturesAsDtoPerEventId(Integer id) {
		return eventPictureRepo.findAllByEventId(id).stream().map(imageMapper::imageToDto).toList();
	}

	public ImageDto getEventWallpaperById(Integer id) {
		List<ImageDto> pictures = getPicturesAsDtoPerEventId(id);
		return pictures.isEmpty() ? null : pictures.get(0);
	}

}
