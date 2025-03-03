package lv.wings.service;

public interface ICRUDInsertedService<T> extends ICRUDService<T> {
	public abstract void create(T element, int parentId) throws Exception;
}
