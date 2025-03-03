package lv.wings.dto.object;

import java.util.ArrayList;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lv.wings.dto.DTOMeta;

@Getter
@Setter
public class EventCategoryDTO extends DTOObject {
	@DTOMeta(sourceField = "eventCategoryId")
	public int id;

	@NotNull
	@Size(min = 3, max = 200, message = "Kategorijas nosaukums nedrīkst saturēt mazāk par 3 vai vairāk par 200 rakstzīmēm!")
	@Pattern(regexp = "^[a-zA-ZāčēģīķļņōŗšūžĀČĒĢĪĶĻŅŌŖŠŪŽ\\s]+$", message = "Kategorijas nosaukums drīkst saturēt tikai burtus un atstarpes!")
	public String title;

	public ArrayList<EventDTO> events;
}
