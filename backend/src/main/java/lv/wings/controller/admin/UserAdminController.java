package lv.wings.controller.admin;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lv.wings.annotation.AllowedSortFields;
import lv.wings.dto.request.admin.AdminPasswordDto;
import lv.wings.dto.request.admin.NewUserDetailsDto;
import lv.wings.dto.response.BasicMessageDto;
import lv.wings.dto.response.admin.users.UserAdminDto;
import lv.wings.dto.response.admin.users.UserDetailsDto;
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
            @RequestParam(name = "sort", defaultValue = "lastActivityDateTime") String sortBy,
            @RequestParam(name = "direction", defaultValue = "desc") String direction) {
        log.info("Received GET request on /api/v1/admin/users");
        return ResponseEntity.ok(userService.getAllEmployees(status, sortBy, direction));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDetailsDto> getEmployeeData(@PathVariable Integer id) {
        log.info("Received GET request on /api/v1/admin/users/{}", id);
        return ResponseEntity.ok(userService.getEmployeeData(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BasicMessageDto> updateEmployeeData(@PathVariable Integer id, @Valid @RequestBody UserDetailsDto dto) {
        log.info("Received PUT request on /api/v1/admin/users/{}", id);
        return ResponseEntity.ok().body(userService.updateUser(id, dto));
    }

    @PatchMapping("/{id}/update-password")
    public ResponseEntity<BasicMessageDto> updatePassword(@PathVariable Integer id, @Valid @RequestBody AdminPasswordDto dto) {
        log.info("Received PATCH request on /api/v1/admin/users/{}/update-password", id);
        return ResponseEntity.ok().body(userService.updatePassword(id, dto));
    }

    @PostMapping
    public ResponseEntity<BasicMessageDto> addNewEmployee(@Valid @RequestBody NewUserDetailsDto dto) {
        log.info("Received POST request on /api/v1/admin/users");
        return ResponseEntity.ok().body(userService.createNewEmployee(dto));
    }
}
