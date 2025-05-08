package lv.wings.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Service;
import lombok.NonNull;
import lv.wings.dto.response.admin.roles.DetailedRoleDto;
import lv.wings.dto.response.admin.roles.RoleDto;
import lv.wings.mapper.RoleMapper;
import lv.wings.model.security.Role;
import lv.wings.repo.RoleRepository;
import lv.wings.service.AbstractCRUDService;
import lv.wings.service.RoleService;

@Service
public class RoleServiceImpl extends AbstractCRUDService<Role, Integer> implements RoleService {

    private final RoleMapper roleMapper;

    public RoleServiceImpl(RoleRepository repository, RoleMapper roleMapper) {
        super(repository, "Role", "entity.role");
        this.roleMapper = roleMapper;
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
}
