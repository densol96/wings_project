package lv.wings.controller.admin;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lv.wings.dto.request.admin.NewRoleDto;
import lv.wings.dto.response.BasicMessageDto;
import lv.wings.dto.response.admin.roles.DetailedRoleDto;
import lv.wings.dto.response.admin.roles.RoleDto;
import lv.wings.service.RoleService;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/admin/roles")
@RequiredArgsConstructor
public class RoleAdminController {

    private final RoleService roleService;

    @GetMapping
    public ResponseEntity<List<RoleDto>> getAllRoles() {
        log.info("Received GET request on /api/v1/admin/roles");
        return ResponseEntity.ok(roleService.getAllRoles());
    }

    @GetMapping("/details")
    public ResponseEntity<List<DetailedRoleDto>> getAllRolesDetailed() {
        log.info("Received GET request on /api/v1/admin/roles/details");
        return ResponseEntity.ok(roleService.getAllRolesWithDetails());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BasicMessageDto> deleteRole(Integer id) {
        log.info("Received DELETE request on /api/v1/admin/roles/{}", id);
        return ResponseEntity.ok(roleService.deleteRole(id));
    }

    @PostMapping
    public ResponseEntity<BasicMessageDto> createRole(NewRoleDto dto) {
        log.info("Received POST request on /api/v1/admin/roles");
        return ResponseEntity.ok(roleService.createRole(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BasicMessageDto> updateRole(Integer id, NewRoleDto dto) {
        log.info("Received DELETE request on /api/v1/admin/roles/{}", id);
        return ResponseEntity.ok(roleService.updateRole(id, dto));
    }
}
