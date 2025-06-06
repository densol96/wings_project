package lv.wings.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import lv.wings.exception.entity.EntityNotFoundException;

import java.util.List;

public abstract class AbstractCRUDService<T, ID> implements CRUDService<T, ID> {

    protected final JpaRepository<T, ID> repository;
    protected final String entityName;
    protected final String entityNameKey;

    public AbstractCRUDService(JpaRepository<T, ID> repository, String entityName, String entityNameKey) {
        this.repository = repository;
        this.entityName = entityName;
        this.entityNameKey = entityNameKey;
    }

    @Override
    public boolean existsById(ID id) {
        return repository.existsById(id);
    }

    @Override
    public long count() {
        return repository.count();
    }

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public T findById(ID id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException(entityNameKey, entityName, id));
    }

    @Override
    public T persist(T entity) {
        return repository.save(entity);
    }

    @Override
    public void deleteById(ID id) {
        repository.deleteById(id);
    }
}
