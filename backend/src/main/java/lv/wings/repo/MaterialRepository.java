package lv.wings.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import lv.wings.model.entity.Material;

public interface MaterialRepository extends JpaRepository<Material, Integer> {

}
