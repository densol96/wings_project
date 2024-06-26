package lv.wings.service;

import java.util.ArrayList;

import lv.wings.model.PasakumaKomentars;

public interface IPasakumaKomentarsService {
	public abstract ArrayList<PasakumaKomentars> retrieveAll() throws Exception;

	public abstract PasakumaKomentars retrieveById(int id) throws Exception;

	public abstract void deleteById(int id) throws Exception;

	public abstract void create(PasakumaKomentars pasakumaKomentars) throws Exception;

	public abstract void update(int id, PasakumaKomentars pasakumaKomentars) throws Exception;
}
