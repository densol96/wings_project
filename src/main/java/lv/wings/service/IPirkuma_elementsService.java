package lv.wings.service;

import java.util.ArrayList;

import lv.wings.model.Pirkuma_elements;

public interface IPirkuma_elementsService {
	//CRUD - create - retrieve - update - delete
	
	//selectAllPirkumaElementi - atgriež visus pirkuma_elementus, kas saglabātas sistēmā
	public abstract ArrayList<Pirkuma_elements> retrieveAll() throws Exception;
	
	//selectPirkuma_elementsById- atgriež vienu pirkuma_elementu pēc tā id
	public abstract Pirkuma_elements retrieveById(int id) throws Exception;

	//deletePirkuma_elementsById- dzēš pirkuma_elementu pēc tā id
	public abstract void deleteById(int id) throws Exception;
	
	//insertNewPirkuma_elements- pievieno jaunu pirkuma_elementu sistēmā
	public abstract void create(Pirkuma_elements pirkuma_elements) throws Exception;
	
	//updatePirkuma_elementsById- rediģē esošo pirkuma_elementu
	public abstract void update(int id, Pirkuma_elements pirkuma_elements) throws Exception;
}
