package lv.wings.repo;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import lv.wings.model.security.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
}
