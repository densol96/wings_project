package lv.wings.service;

import lv.wings.model.entity.Event;
import lv.wings.model.entity.EventCategory;

public interface EventCategoryService extends CRUDService<EventCategory, Integer> {
    String getCategoryTitleByEvent(Event event);
}
