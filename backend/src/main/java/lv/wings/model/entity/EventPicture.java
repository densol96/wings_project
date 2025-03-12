package lv.wings.model.entity;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import lv.wings.model.base.TranslatableEntity;
import lv.wings.model.translation.EventPictureTranslation;

@Entity
@Table(name = "event_pictures")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@Data
public class EventPicture extends TranslatableEntity<EventPictureTranslation> {

	@Column(nullable = false)
	private String src;

	@ManyToOne
	@JoinColumn(name = "event_id")
	private Event event;

	@Builder
	public EventPicture(String src, Event event) {
		setSrc(src);
		setEvent(event);
	}

}
