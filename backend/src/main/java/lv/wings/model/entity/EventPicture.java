package lv.wings.model.entity;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lv.wings.model.interfaces.Imagable;

@Entity
@Table(name = "event_pictures")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@Data
@SQLDelete(sql = "UPDATE event_pictures SET deleted = true WHERE event_pictures_id=?")
@Where(clause = "deleted=false")
public class EventPicture extends AuditableEntity implements Imagable {

	@Column(nullable = false)
	private String src;

	@NotNull
	@Column(nullable = false)
	private String alt;

	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "event_id")
	private Event event;

	// Soft delete
	@Column(name = "deleted")
	private boolean deleted = false;

	@Builder
	public EventPicture(String src, String alt, Event event) {
		setSrc(src);
		setAlt(alt);
		setEvent(event);
	}

}