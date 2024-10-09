package lv.wings.service;

import java.util.ArrayList;

import lv.wings.model.PasakumaBilde;

public interface IPasakumaBildeService {
	public abstract ArrayList<PasakumaBilde> retrieveAll() throws Exception;

	public abstract PasakumaBilde retrieveById(int id) throws Exception;

	public abstract void deleteById(int id) throws Exception;

	public abstract void create(PasakumaBilde pasakumaBilde) throws Exception;

	public abstract void update(int id, PasakumaBilde pasakumaBilde) throws Exception;

}
