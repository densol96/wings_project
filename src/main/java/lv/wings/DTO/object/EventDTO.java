package lv.wings.dto.object;

import java.util.ArrayList;
import java.util.Date;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lv.wings.dto.DTOMeta;
import lv.wings.model.Event;

@Getter
@Setter
public class EventDTO extends DTOObject {
	@DTOMeta(sourceField = "eventId")
	public int id;

	public Date startDate;

	public Date endDate;

	@Size(min = 5, max = 300, message = "Nosaukums nedrīkst saturēt mazāk par 5 vai vairāk par 300 rakstzīmēm!")
	public String title;

	@NotNull
	public String location;

	public String description;

	// public String keyWords;

	public ArrayList<EventPictureDTO> eventPictures;

	public EventCategoryDTO eventCategory;

	@DTOMeta(ignore = true)
	public long timeLeftForEvent;

	// public LocalDateTime createDate;

	@Override
	public void onFinish(Object source) {
		super.onFinish(source);
		Event event = (Event) source;
		this.timeLeftForEvent = new Date().getTime() - event.getStartDate().getTime();
	}
}
