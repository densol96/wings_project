package lv.wings.repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import lv.wings.enums.Country;
import lv.wings.model.entity.Terminal;

public interface TerminalRepository extends JpaRepository<Terminal, Integer> {
    List<Terminal> findAllByCountryAndDeletedFalse(Country country);
}
