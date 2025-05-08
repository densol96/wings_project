package lv.wings.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import lombok.NonNull;

import lv.wings.dto.request.admin.NewRoleDto;
import lv.wings.dto.response.BasicMessageDto;
import lv.wings.dto.response.admin.roles.DetailedRoleDto;
import lv.wings.dto.response.admin.roles.RoleDto;
import lv.wings.mapper.RoleMapper;
import lv.wings.model.security.Role;
import lv.wings.repo.RoleRepository;
import lv.wings.service.AbstractCRUDService;
import lv.wings.service.PermissionService;
import lv.wings.service.RoleService;

@Service
public class RoleServiceImpl extends AbstractCRUDService<Role, Integer> implements RoleService {

    private final RoleMapper roleMapper;
    private final PermissionService permissionService;

    public RoleServiceImpl(RoleRepository repository, RoleMapper roleMapper, PermissionService permissionService) {
        super(repository, "Role", "entity.role");
        this.roleMapper = roleMapper;
        this.permissionService = permissionService;
    }

    @Override
    public Set<Role> findByIds(@NonNull Set<Integer> ids) {
        return new HashSet<>(repository.findAllById(ids));
    }

    @Override
    public List<RoleDto> getAllRoles() {
        return repository.findAll().stream().map(roleMapper::toDto).toList();
    }

    @Override
    public List<DetailedRoleDto> getAllRolesWithDetails() {
        return repository.findAll().stream().map(roleMapper::toDetailedDto).toList();
    }

    @Override
    public BasicMessageDto updateRole(Integer id, NewRoleDto dto) {
        Role role = findById(id);
        role.setName(dto.getName());
        role.setPermissions(permissionService.validatePermissionInputAndReturnEntities(dto.getPermissionCodes()));
        repository.save(role);
        return new BasicMessageDto("Loma veiksmīgi atjaunināta.");
    }

    @Override
    public BasicMessageDto createRole(NewRoleDto dto) {
        Role role = new Role();
        role.setName(dto.getName());
        role.setPermissions(permissionService.validatePermissionInputAndReturnEntities(dto.getPermissionCodes()));
        repository.save(role);
        return new BasicMessageDto("Loma veiksmīgi izveidota.");
    }

    @Override
    public BasicMessageDto deleteRole(Integer id) {
        repository.delete(findById(id));
        return new BasicMessageDto("Loma veiksmīgi dzēsta.");
    }
}
