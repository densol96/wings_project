package lv.wings.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lv.wings.dto.DTOMapper;
import lv.wings.dto.object.EventDTO;
import lv.wings.enums.SortBy;
import lv.wings.exception.old.NoContentException;
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
			@RequestParam(defaultValue = "1") @Min(1) Integer page,
			@RequestParam(defaultValue = "4") @Min(1) Integer resultsPerPage,
			@RequestParam(defaultValue = "CREATEDAT") SortBy sortBy) {

		return ResponseEntity.ok("In testing ==> " + sortBy.toString());

		// Pageable pageable = PageRequest.of(page - 1, resultsPerPage,
		// Sort.by("createDate").descending());
		// Page<Event> allEvents = eventsService.retrieveAll(pageable);
		// ArrayList<EventDTO> eventsDTO = DTOMapper.mapMany(EventDTO.class,
		// allEvents.toList().toArray(),
		// new String[] { "eventCategory.events" });
		// Page<Event> evts = new PageImpl(eventsDTO, pageable,
		// allEvents.getTotalElements());
		// return ResponseEntity.ok(new ApiResponse<>(null, evts));

	}

	@GetMapping(value = "/show/{id}")
	public ResponseEntity<ApiResponse<?>> getSingleNews(@PathVariable("id") int id) {
		try {
			Event event = eventsService.retrieveById(id);
			EventDTO eventDTO = DTOMapper.map(EventDTO.class, event,
					new String[] { "eventCategory.events", "eventPictures.event" });

			return ResponseEntity.ok(new ApiResponse<>(null, eventDTO));
		} catch (Exception e) {
			return ResponseEntity.ok(new ApiResponse<>(e.getMessage(), null));
		}
	}
}