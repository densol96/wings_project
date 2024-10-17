package lv.wings.service;

import java.util.ArrayList;

import lv.wings.model.Pasakums;

public interface IPasakumiFilteringService {
	ArrayList<Pasakums> selectAllPasakumiDescOrder() throws Exception;

	ArrayList<Pasakums> selectAllPasakumiAscOrder() throws Exception;
}
