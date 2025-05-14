package lv.wings.repo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import io.lettuce.core.dynamic.annotation.Param;
import jakarta.transaction.Transactional;
import lv.wings.model.security.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.lastActivityDateTime = :time WHERE u.id = :id")
    void updateLastActivity(@Param("id") Integer id, @Param("time") LocalDateTime time);

    List<User> findAll(Sort sort);

    List<User> findByAccountLockedFalseAndAccountBannedFalse(Sort sort);

    List<User> findByAccountLockedTrueOrAccountBannedTrue(Sort sort);

    @Query(value = "SELECT * FROM users WHERE username = 'system'", nativeQuery = true)
    User findSystemUserNative();
}
