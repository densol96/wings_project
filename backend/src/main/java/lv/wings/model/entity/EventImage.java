package lv.wings.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lv.wings.model.base.ImageableEntity;
import lv.wings.model.translation.EventImageTranslation;

@Entity
@Table(name = "event_images")
@NoArgsConstructor
@Getter
@Setter
public class EventImage extends ImageableEntity<EventImageTranslation, Event> {

	@Builder
	public EventImage(Event event, String src, Integer position) {
		super(event, src, position);
	}

}
