package lv.wings.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import lv.wings.model.entity.Color;

public interface ColorRepository extends JpaRepository<Color, Integer> {

}
