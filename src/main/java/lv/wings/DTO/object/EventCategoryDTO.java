package lv.wings.dto.object;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;
import lv.wings.dto.DTOMeta;

@Getter
@Setter
public class EventCategoryDTO extends DTOObject {
	@DTOMeta(sourceField = "eventCategoryId")
	public int id;
	public String title;

	public ArrayList<EventDTO> events;
}
