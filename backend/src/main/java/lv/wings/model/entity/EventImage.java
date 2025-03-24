package lv.wings.model.entity;


import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Table;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import lv.wings.model.base.ImageableEntity;
import lv.wings.model.translation.EventImageTranslation;

@Entity
@Table(name = "event_images")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@Data
public class EventImage extends ImageableEntity<EventImageTranslation, Event> {

	@Builder
	public EventImage(Event event, String src) {
		super(event, src);
	}

}
