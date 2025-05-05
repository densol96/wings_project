package lv.wings.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import lv.wings.model.security.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

}
