package lv.wings.service;

import java.util.ArrayList;

import lv.wings.model.Prece;

public interface IPreceService {
	//CRUD - create - retrieve - update - delete
	
	//selectAllPreces - atgriež visas preces, kas saglabātas sistēmā
	public abstract ArrayList<Prece> retrieveAll() throws Exception;
	
	//selectPreceById- atgriež vienu preci pēc tā id
	public abstract Prece retrieveById(int id) throws Exception;
	
	//deletePreceById- dzēš preci pēc tā id
	public abstract void deleteById(int id) throws Exception;
	
	//insertNewPrece- pievieno jaunu preci sistēmā
	public abstract void create(Prece prece) throws Exception;
	
	//updatePreceById- rediģē esošo preci
	public abstract void update(int id, Prece prece) throws Exception;

}
