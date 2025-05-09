package lv.wings.controller.admin;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lv.wings.dto.response.admin.roles.PermissionDto;
import lv.wings.service.SecurityEventService;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/admin/security-events")
@RequiredArgsConstructor
public class SecuityEventAdminController {

    private final SecurityEventService securityEventService;

    @GetMapping
    public ResponseEntity<List<PermissionDto>> getSecurityEvents(
            @RequestParam(name = "q", required = false) String q) {
        log.info("Received GET request on /api/v1/admin/permissions");
        return ResponseEntity.ok(permissionService.getAll());
    }
}
