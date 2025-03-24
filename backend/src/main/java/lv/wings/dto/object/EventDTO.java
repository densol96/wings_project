package lv.wings.dto.object;

import java.util.ArrayList;
import java.util.Date;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lv.wings.dto.DTOMeta;

@Getter
@Setter
public class EventDTO extends DTOObject {
	@DTOMeta(sourceField = "eventId")
	public int id;

	@NotNull
	public Date startDate;

	@NotNull
	public Date endDate;

	@Size(min = 5, max = 300, message = "Nosaukums nedrīkst saturēt mazāk par 5 vai vairāk par 300 rakstzīmēm!")
	public String title;

	@NotNull
	@Size(min = 2, max = 200, message = "Vieta nedrīkst saturēt mazāk par 2 vai vairāk par 200 rakstzīmēm!")
	public String location;

	@NotNull
	@Size(min = 0, max = 3000, message = "Aprakstā par daudz rakstzīmju! (0-3000)")
	public String description;

	// public String keyWords;

	public ArrayList<EventPictureDTO> eventPictures;

	public EventCategoryDTO eventCategory;

	// @DTOMeta(ignore = true)
	// public long timeLeftForEvent;

	// public LocalDateTime createDate;

	/*
	 * @Override
	 * public void onFinish(Object source) {
	 * super.onFinish(source);
	 * Event event = (Event) source;
	 * this.timeLeftForEvent = new Date().getTime() -
	 * event.getStartDate().getTime();
	 * }
	 */
}
