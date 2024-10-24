package lv.wings.service;

import java.util.ArrayList;

import lv.wings.model.Event;

public interface IPasakumiFilteringService {
	ArrayList<Event> selectAllEventsDescOrder() throws Exception;

	ArrayList<Event> selectAllEventsAscOrder() throws Exception;
}
