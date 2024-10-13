package lv.wings.service;

import java.util.ArrayList;

import lv.wings.model.PasakumaKategorija;

public interface IPasakumaKategorijaService {

	public abstract ArrayList<PasakumaKategorija> retrieveAll() throws Exception;

	public abstract PasakumaKategorija retrieveById(int id) throws Exception;

	public abstract void deleteById(int id) throws Exception;

	public abstract void create(PasakumaKategorija pasakumaKategorija) throws Exception;

	public abstract void update(int id, PasakumaKategorija pasakumaKategorija) throws Exception;

}
