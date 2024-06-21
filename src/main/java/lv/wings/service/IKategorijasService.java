package lv.wings.service;

import java.util.ArrayList;

import lv.wings.model.Kategorijas;

public interface IKategorijasService {
	//CRUD - create - retrieve - update - delete
	
	//selectAllKategorijas - atgriež visas kategorijas, kas saglabātas sistēmā
	public abstract ArrayList<Kategorijas> retrieveAll() throws Exception;
	
	//selectKategorijaById- atgriež vienu kategoriju pēc tā id
	public abstract Kategorijas retrieveById(int id) throws Exception;
	
	//deleteKategorijaById- dzēš kategoriju pēc tā id
	public abstract void deleteById(int id) throws Exception;
	
	//insertNewKategorija- pievieno jaunu kategoriju sistēmā
	public abstract void create(Kategorijas kategorija) throws Exception;
	
	//updateKategorijaById- rediģē esošo kategoriju
	public abstract void update(int id, Kategorijas kategorija) throws Exception;
}
