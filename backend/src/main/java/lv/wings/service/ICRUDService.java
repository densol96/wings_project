package lv.wings.service;

import java.util.List;

import org.springframework.data.domain.Page;

public interface ICRUDService<T> {
	public abstract List<T> retrieveAll();

	public abstract Page<T> retrieveAll(Integer page, Integer size, String sortBy, String sortDirection);

	public abstract T retrieveById(Integer id);

	public abstract void deleteById(Integer id);

	public abstract void create(T element);

	public abstract void update(Integer id, T element);
}
