package lv.wings.controller.admin;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lv.wings.annotation.AllowedSortFields;
import lv.wings.dto.response.admin.users.UserAdminDto;
import lv.wings.service.UserService;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/admin/users")
@RequiredArgsConstructor
public class UserAdminController {
    private final UserService userService;

    @GetMapping
    @AllowedSortFields({"lastActivityDateTime", "joinDateTime"})
    public ResponseEntity<List<UserAdminDto>> getAllEmployees(
            @RequestParam(name = "status", defaultValue = "all") String status,
            @RequestParam(name = "sortBy", defaultValue = "lastActivityDateTime") String sortBy,
            @RequestParam(name = "direction", defaultValue = "desc") String direction) {
        log.info("Admin requested list of all users (GET /api/v1/admin/users)");
        return ResponseEntity.ok(userService.getAllEmployees(status, sortBy, direction));
    }
}
