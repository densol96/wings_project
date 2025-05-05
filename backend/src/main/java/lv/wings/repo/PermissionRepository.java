package lv.wings.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import lv.wings.model.security.Permission;

public interface PermissionRepository extends JpaRepository<Permission, Integer> {

}
