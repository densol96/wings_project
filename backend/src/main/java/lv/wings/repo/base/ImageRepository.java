package lv.wings.repo.base;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import lv.wings.model.base.ImageableEntity;

@NoRepositoryBean
public interface ImageRepository<T extends ImageableEntity<?, ?>, ID> extends JpaRepository<T, ID> {
    List<T> findAllByOwnerId(Integer id);

    List<T> findAllByOwnerId(Integer id, Pageable pageable);

    T findBySrc(String src);
}
