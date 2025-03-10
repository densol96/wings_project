package lv.wings.service;

import java.util.List;

import lv.wings.model.entity.Event;

public interface IPasakumiFilteringService {
	List<Event> selectAllEventsDescOrder() throws Exception;

	List<Event> selectAllEventsAscOrder() throws Exception;
}
