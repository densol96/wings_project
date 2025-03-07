package lv.wings.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICRUDService<T> {
	public abstract List<T> retrieveAll() throws Exception;

	public abstract Page<T> retrieveAll(Pageable pageable) throws Exception;

	public abstract T retrieveById(Integer id) throws Exception;

	public abstract void deleteById(Integer id) throws Exception;

	public abstract void create(T element) throws Exception;

	public abstract void update(Integer id, T element) throws Exception;
}
