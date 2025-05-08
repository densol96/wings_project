package lv.wings.controller.admin;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
}
