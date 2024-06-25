package lv.wings.service;

import java.util.ArrayList;

import lv.wings.model.Preces_bilde;

public interface IPreces_bildeService {
	//CRUD - create - retrieve - update - delete
	
	//selectAllPrecesBildes - atgriež visas preces_bildes, kas saglabātas sistēmā
	public abstract ArrayList<Preces_bilde> retrieveAll() throws Exception;
	
	//selectPrecesBildeById- atgriež vienu preces_bildi pēc tā id
	public abstract Preces_bilde retrieveById(int id) throws Exception;
	
	//deletePreces_bildeById- dzēš preces_bildi pēc tā id
	public abstract void deleteById(int id) throws Exception;
	
	//insertNewPreces_bilde- pievieno jaunu preces_bildi sistēmā
	public abstract void create(Preces_bilde bilde, int preceID) throws Exception;
	
	//updatePrece_bildeById- rediģē esošo preces_bildi
	public abstract void update(int id, Preces_bilde bilde) throws Exception;

}
