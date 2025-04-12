package lv.wings.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import lv.wings.model.entity.Terminal;

public interface TerminalRepository extends JpaRepository<Terminal, Integer> {

}
