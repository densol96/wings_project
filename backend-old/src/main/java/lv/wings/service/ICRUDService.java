package lv.wings.service;

import java.util.ArrayList;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICRUDService <T> {
	public abstract ArrayList<T> retrieveAll() throws Exception;
	public abstract Page<T> retrieveAll(Pageable pageable) throws Exception;

	public abstract T retrieveById(int id) throws Exception;

	public abstract void deleteById(int id) throws Exception;

	public abstract void create(T element) throws Exception;

	public abstract void update(int id, T element) throws Exception;
}
