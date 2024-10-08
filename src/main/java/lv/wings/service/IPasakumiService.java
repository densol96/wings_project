package lv.wings.service;

import java.util.ArrayList;

import lv.wings.exceptions.NoContentException;
import lv.wings.model.Pasakums;

public interface IPasakumiService {

	Pasakums selectPaskumsById(int id) throws Exception;

	ArrayList<Pasakums> selectAllPasakumi() throws NoContentException;

	ArrayList<Pasakums> selectAllPasakumiDescOrder() throws Exception;

	ArrayList<Pasakums> selectAllPasakumiAscOrder() throws Exception;

	void deletePasakumiById(int id) throws Exception;

	void create(Pasakums pasakums) throws Exception;

	void update(int id, Pasakums pasakums) throws Exception;

}
