package lv.wings.service;

import java.util.List;

import lv.wings.model.Event;

public interface IPasakumiFilteringService {
	List<Event> selectAllEventsDescOrder() throws Exception;

	List<Event> selectAllEventsAscOrder() throws Exception;
}
