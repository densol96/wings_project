package lv.wings.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import lv.wings.dto.DTOMapper;
import lv.wings.dto.object.EventDTO;
import lv.wings.enums.SortBy;
import lv.wings.model.Event;
import lv.wings.responses.ApiResponse;
import lv.wings.service.ICRUDService;

@RestController
@RequestMapping(value = "/api/v1/events")
@RequiredArgsConstructor
// @Validated
public class EventController {

	private final ICRUDService<Event> eventsService;

	@GetMapping
	public ResponseEntity<String> getLatestEvents(
			@RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "4") Integer size,
			@RequestParam(defaultValue = "CREATEDAT") SortBy sortBy,
			@RequestParam(defaultValue = "desc") String sortDirection) {

		Pageable pageable = PageRequest.of(
				page,
				size,
				Sort.by(Sort.Direction.fromString(sortDirection), sortBy.toString()));

		Page<Event> allEvents = eventsService.retrieveAll(pageable);
		ArrayList<EventDTO> eventsDTO = DTOMapper.mapMany(EventDTO.class,
				allEvents.toList().toArray(),
				new String[] { "eventCategory.events" });
		Page<Event> evts = new PageImpl(eventsDTO, pageable,
				allEvents.getTotalElements());
		return ResponseEntity.ok(new ApiResponse<>(null, evts));

	}

	@GetMapping(value = "/show/{id}")
	public ResponseEntity<ApiResponse<?>> getSingleNews(@PathVariable("id") int id) {
		Event event = eventsService.retrieveById(id);
		EventDTO eventDTO = DTOMapper.map(EventDTO.class, event,
				new String[] { "eventCategory.events", "eventPictures.event" });

		return ResponseEntity.ok(new ApiResponse<>(null, eventDTO));
	}
}