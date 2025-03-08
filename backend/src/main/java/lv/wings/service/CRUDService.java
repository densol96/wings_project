package lv.wings.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CRUDService<T, ID> {
    List<T> findAll();

    Page<T> findAll(Pageable pageable);

    T findById(ID id);

    T create(T entity);

    T update(ID id, T entity);

    void deleteById(ID id);
}
