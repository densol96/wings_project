package lv.wings.service;

import java.util.ArrayList;

import lv.wings.model.Atlaide;

public interface IAtlaideService {

	public abstract ArrayList<Atlaide> retrieveAll() throws Exception;

	public abstract Atlaide retrieveById(int id) throws Exception;

	public abstract void deleteById(int id) throws Exception;

	public abstract void create(Atlaide atlaide) throws Exception;

	public abstract void update(int id, Atlaide atlaide) throws Exception;

}
