package lv.wings.repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import lv.wings.model.entity.Color;

public interface ColorRepository extends JpaRepository<Color, Integer> {
    List<Color> findAllByIdIn(List<Integer> ids);
}
