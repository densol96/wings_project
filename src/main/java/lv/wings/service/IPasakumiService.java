package lv.wings.service;

import java.util.ArrayList;

import lv.wings.model.PasakumaBilde;
import lv.wings.model.Pasakums;

public interface IPasakumiService {

	//ArrayList<Pasakums> selectAllPasakumi() throws Exception;

	ArrayList<Pasakums> selectAllPasakumi() throws Exception;
	
	ArrayList<Pasakums> selectAllPasakumiDescOrder() throws Exception;
	
	ArrayList<Pasakums> selectAllPasakumiAscOrder() throws Exception;
	
}
