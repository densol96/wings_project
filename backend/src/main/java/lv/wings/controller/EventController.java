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
import lombok.extern.slf4j.Slf4j;
import lv.wings.annotation.AllowedSortFields;
import lv.wings.dto.response.event.ShortEventDto;
import lv.wings.dto.response.event.SingleEventDto;
import lv.wings.service.EventService;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/events")
@RequiredArgsConstructor
public class EventController {

	private final EventService eventsService;

	@GetMapping
	@AllowedSortFields({"lastModifiedAt", "startDate", "createdAt"})
	public ResponseEntity<Page<ShortEventDto>> getLatestEvents(
			@PageableDefault(page = 1, size = 6, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
		log.info("Received GET request on /api/v1/events");
		return ResponseEntity.ok(eventsService.getPublicEvents(pageable));
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<SingleEventDto> getSingleNews(@PathVariable("id") Integer id) {
		log.info("Received GET request on /api/v1/{}", id);
		return ResponseEntity.ok(eventsService.getPublicEvent(id));
	}
}
