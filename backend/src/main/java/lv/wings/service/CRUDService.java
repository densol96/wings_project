package lv.wings.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import lv.wings.exception.entity.EntityNotFoundException;

public interface CRUDService<T, ID> {

    boolean existsById(ID id);

    long count();

    List<T> findAll();

    Page<T> findAll(Pageable pageable);

    /**
     * @throws EntityNotFoundException if entity not found
     */
    T findById(ID id);

    T create(T entity);

    T update(ID id, T entity);

    void deleteById(ID id);
}
