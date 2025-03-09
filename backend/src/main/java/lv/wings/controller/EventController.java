package lv.wings.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import lv.wings.dto.response.event.PublicEventDto;
import lv.wings.service.EventService;

@RestController
@RequestMapping(value = "/api/v1/events")
@RequiredArgsConstructor
public class EventController {

	private final EventService eventsService;

	@GetMapping
	public ResponseEntity<Page<PublicEventDto>> getLatestEvents(
			@PageableDefault(page = 1, size = 5, sort = "createdAt", direction = Sort.Direction.ASC) Pageable pageable) {

		Sort sort = pageable.getSort();

		System.out.println("CONTROLLER sortBy => " + sort.toList().get(0).getProperty());
		System.out.println("CONTROLLER sortDirection => " + sort.toList().get(0).getDirection());

		return ResponseEntity.ok(eventsService.getEvents(pageable));
	}

	@GetMapping(value = "/show/{id}")
	public ResponseEntity<PublicEventDto> getSingleNews(@PathVariable("id") Integer id) {
		return ResponseEntity.ok(eventsService.getEvent(id));
	}
}