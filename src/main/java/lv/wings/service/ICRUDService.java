package lv.wings.service;

import java.util.ArrayList;

public interface ICRUDService <T> {
	public abstract ArrayList<T> retrieveAll() throws Exception;

	public abstract T retrieveById(int id) throws Exception;

	public abstract void deleteById(int id) throws Exception;

	public abstract void create(T element) throws Exception;

	public abstract void update(int id, T element) throws Exception;
}
