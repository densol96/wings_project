package lv.wings.controller.admin;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import lv.wings.dto.response.admin.security_events.SecurityEventDto;
import lv.wings.service.SecurityEventService;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/admin/security/events")
@RequiredArgsConstructor
public class SecuityEventAdminController {

    private final SecurityEventService securityEventService;

    @GetMapping
    public ResponseEntity<Page<SecurityEventDto>> getSecurityEvents(
            @RequestParam(required = false) String q,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(name = "type", required = false) String securityEventType) {
        log.info("Received GET request on /api/v1/admin/permissions");
        return ResponseEntity.ok(securityEventService.getSecurityEvents(page, size, q, securityEventType));
    }
}
