package lv.wings.repo;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import lv.wings.model.entity.GlobalParam;

public interface GlobalParamsRepository extends JpaRepository<GlobalParam, Integer> {

    Optional<GlobalParam> findByTitle(String title);

}
