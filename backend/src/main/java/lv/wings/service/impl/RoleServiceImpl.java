package lv.wings.service.impl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import lv.wings.dto.request.admin.NewRoleDto;
import lv.wings.dto.response.BasicMessageDto;
import lv.wings.dto.response.admin.roles.DetailedRoleDto;
import lv.wings.dto.response.admin.roles.RoleDto;
import lv.wings.mapper.RoleMapper;
import lv.wings.model.security.Permission;
import lv.wings.model.security.Role;
import lv.wings.model.security.User;
import lv.wings.repo.RoleRepository;
import lv.wings.service.AbstractCRUDService;
import lv.wings.service.PermissionService;
import lv.wings.service.RoleService;
import lv.wings.service.UserService;

@Service
public class RoleServiceImpl extends AbstractCRUDService<Role, Integer> implements RoleService {

    private final RoleMapper roleMapper;
    private final PermissionService permissionService;
    private final UserService userService;

    public RoleServiceImpl(RoleRepository repository, RoleMapper roleMapper, PermissionService permissionService, @Lazy UserService userService) {
        super(repository, "Role", "entity.role");
        this.roleMapper = roleMapper;
        this.permissionService = permissionService;
        this.userService = userService;
    }

    @Override
    public List<Role> findByIds(List<Integer> ids) {
        if (ids == null || ids.isEmpty())
            return List.of();
        return repository.findAllById(ids);
    }

    @Override
    public List<RoleDto> getAllRoles() {
        return repository.findAll().stream().map(roleMapper::toDto).toList();
    }

    @Override
    public DetailedRoleDto getRoleData(Integer id) {
        return roleMapper.toDetailedDto(findById(id));
    }

    @Override
    public List<DetailedRoleDto> getAllRolesWithDetails(List<Integer> permissions) {
        List<Role> roles = repository.findAll();

        List<Role> filteredRoles;
        if (permissions != null && !permissions.isEmpty()) {
            filteredRoles = roles.stream()
                    .filter(role -> {
                        Set<Integer> rolePermissionIds = role.getPermissions().stream()
                                .map(Permission::getId)
                                .collect(Collectors.toSet());
                        return rolePermissionIds.containsAll(permissions);
                    })
                    .collect(Collectors.toList());
        } else {
            filteredRoles = roles;
        }

        return filteredRoles.stream()
                .map(roleMapper::toDetailedDto)
                .toList();
    }

    @Override
    public BasicMessageDto updateRole(Integer id, NewRoleDto dto) {
        Role role = findById(id);
        role.setName(dto.getName());
        role.setPermissions(permissionService.validatePermissionInputAndReturnEntities(dto.getPermissionIds()));
        repository.save(role);
        return new BasicMessageDto("Loma veiksmīgi atjaunināta.");
    }

    @Override
    public BasicMessageDto createRole(NewRoleDto dto) {
        Role role = new Role();
        role.setName(dto.getName());
        role.setPermissions(permissionService.validatePermissionInputAndReturnEntities(dto.getPermissionIds()));
        repository.save(role);
        return new BasicMessageDto("Loma veiksmīgi izveidota.");
    }

    @Override
    public BasicMessageDto deleteRole(Integer id) {
        Role role = findById(id);
        for (User user : role.getUsers()) {
            user.getRoles().remove(role);
            userService.persist(user);
        }
        repository.delete(role);
        return new BasicMessageDto("Loma veiksmīgi dzēsta.");
    }
}
