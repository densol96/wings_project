package lv.wings.repo.base;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import io.lettuce.core.dynamic.annotation.Param;
import lv.wings.model.base.ImageableEntity;

@NoRepositoryBean
public interface ImageRepository<T extends ImageableEntity<?, ?>, ID> extends JpaRepository<T, ID> {
    List<T> findAllByOwnerId(Integer id);

    List<T> findAllByOwnerId(Integer id, Pageable pageable);

    T findBySrc(String src);

    @Query("SELECT COALESCE(MAX(i.position), 0) FROM #{#entityName} i WHERE i.owner.id = :ownerId")
    int findMaxPositionByOwnerId(@Param("ownerId") Integer ownerId);

}
