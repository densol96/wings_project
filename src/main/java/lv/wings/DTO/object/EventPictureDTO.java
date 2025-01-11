package lv.wings.dto.object;

import lombok.Getter;
import lombok.Setter;
import lv.wings.dto.DTOMeta;

@Getter
@Setter
public class EventPictureDTO extends DTOObject {
	@DTOMeta(sourceField = "eventPicturesId")
	public int id;
	public String referenceToPicture;
	// public String title;
	// public String description;
}
