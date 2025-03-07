package lv.wings.model;

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

@Entity
@Table(name = "event_pictures")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@Data
@SQLDelete(sql = "UPDATE event_pictures SET deleted = true WHERE event_pictures_id=?")
@Where(clause = "deleted=false")
public class EventPicture extends AuditableEntity {

	@NotNull
	@Column(nullable = false, unique = true)
	private String imageUrl;

	@NotNull
	@Column(nullable = false)
	@Size(min = 5, max = 200, message = "Nosaukums nedrīkst saturēt mazāk par 5 vai vairāk par 200 rakstzīmēm!")
	private String title;

	@NotNull
	@Column(nullable = false)
	@Size(min = 0, max = 150, message = "Aprakstā par daudz rakstzīmju! (0-3000)")
	private String description;

	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "event_id")
	private Event event;

	// Soft delete
	@Column(name = "deleted")
	private boolean deleted = false;

	@Builder
	public EventPicture(String imageUrl, String title, String description, Event event) {
		setImageUrl(imageUrl);
		setTitle(title);
		setDescription(description);
		setEvent(event);
	}

}