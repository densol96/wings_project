package lv.wings.controller.admin;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lv.wings.dto.response.BasicMessageDto;
import lv.wings.dto.response.admin.images.AdminImageDto;
import lv.wings.model.entity.Event;
import lv.wings.model.entity.EventImage;
import lv.wings.service.ImageService;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/admin/events")
@RequiredArgsConstructor
public class EventImageAdminController {
    private final ImageService<EventImage, Event, Integer> eventImageService;

    @GetMapping("/{eventId}/images")
    public ResponseEntity<List<AdminImageDto>> getImagesPerEvent(@PathVariable Integer eventId) {
        log.info("Received GET request on /api/v1/admin/events/{}/images", eventId);
        return ResponseEntity.ok(eventImageService.getAdminImages(eventId));
    }

    @PostMapping(path = "/{eventId}/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BasicMessageDto> uploadImagesPerEvent(
            @PathVariable Integer eventId,
            @RequestParam("images") List<MultipartFile> images) {
        log.info("Received POST request on /api/v1/admin/events/{}/images", eventId);
        return ResponseEntity.status(HttpStatus.CREATED).body(eventImageService.addMoreImages(eventId, images));
    }

    @DeleteMapping("/images/{imageId}")
    public ResponseEntity<BasicMessageDto> deleteImageFromEvent(@PathVariable Integer imageId) {
        log.info("Received DELETE request on /api/v1/admin/events/images/{}", imageId);
        return ResponseEntity.ok(eventImageService.deleteImage(imageId));
    }
}

