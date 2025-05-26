package lv.wings.controller.admin;

import java.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import lv.wings.annotation.AllowedSortFields;
import lv.wings.dto.request.admin.events.NewEventDto;
import lv.wings.dto.response.BasicMessageDto;
import lv.wings.dto.response.admin.events.EventAdminDto;
import lv.wings.dto.response.admin.events.ExistingEventDto;
import lv.wings.service.EventService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;



@Slf4j
@RestController
@RequestMapping(value = "/api/v1/admin/events")
@RequiredArgsConstructor
public class EventAdminController {

    private final EventService eventService;

    @GetMapping
    @AllowedSortFields({"createdAt", "lastModifiedAt"})
    public ResponseEntity<Page<EventAdminDto>> getEvents(
            @PageableDefault(page = 1, size = 5, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(defaultValue = "") String q,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        log.info("Received GET request on /api/v1/admin/events");
        return ResponseEntity.ok(eventService.getAllEventsForAdmin(q, pageable, start, end));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExistingEventDto> getEvent(@PathVariable Integer id) {
        log.info("Received GET request on /api/v1/admin/events/{}", id);
        return ResponseEntity.ok(eventService.getExistingProductForAdmin(id));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<BasicMessageDto> deleteEvent(@PathVariable Integer id) {
        log.info("Received DELETE request on /api/v1/admin/events/{}", id);
        return ResponseEntity.ok(eventService.deleteEvent(id));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BasicMessageDto> createEvent(@ModelAttribute NewEventDto dto) {
        log.info("Received POST request on /api/v1/admin/events/");
        return ResponseEntity.ok(eventService.createEvent(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BasicMessageDto> updateEvent(@PathVariable Integer id, @ModelAttribute NewEventDto dto) {
        log.info("Received PUT request on /api/v1/admin/events/");
        return ResponseEntity.ok(eventService.updateEvent(dto, id));
    }
}
