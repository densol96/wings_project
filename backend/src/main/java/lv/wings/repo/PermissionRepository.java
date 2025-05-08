package lv.wings.repo;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import lv.wings.enums.PermissionName;
import lv.wings.model.security.Permission;

public interface PermissionRepository extends JpaRepository<Permission, Integer> {
    Optional<Permission> findByName(PermissionName name);
}
