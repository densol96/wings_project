package lv.wings.repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import lv.wings.model.base.ImageableEntity;

@NoRepositoryBean
public interface ImageRepository<T extends ImageableEntity<?, ?>, ID> extends JpaRepository<T, ID> {
    List<T> findAllByOwnerId(Integer id);

    T findBySrc(String src);
}

// public interface ImageRepository<T extends ImageableEntity<L, O>, L extends Localable, O extends HasImages<T>, ID> extends JpaRepository<T, ID> {
// List<T> findAllByOwnerId(Integer id);

// T findBySrc(String src);
// }
