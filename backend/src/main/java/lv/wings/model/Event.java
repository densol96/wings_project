package lv.wings.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "events")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@Data
@SQLDelete(sql = "UPDATE event SET deleted = true WHERE event_id=?")
@Where(clause = "deleted=false")
public class Event extends AuditableEntity {
	@NotNull
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date startDate;

	@NotNull
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date endDate;

	@NotNull
	@Size(min = 5, max = 300, message = "Nosaukums nedrīkst saturēt mazāk par 5 vai vairāk par 300 rakstzīmēm!")
	private String title;

	@NotNull
	@Size(min = 2, max = 200, message = "Vieta nedrīkst saturēt mazāk par 2 vai vairāk par 200 rakstzīmēm!")
	private String location;

	@NotNull
	@Size(min = 0, max = 1000, message = "Aprakstā par daudz rakstzīmju! (0-3000)")
	private String description;

	@NotNull
	private String keyWords;

	@ManyToOne
	@JsonManagedReference
	@JoinColumn(name = "event_category_id")
	private EventCategory category;

	@OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	private Collection<EventPicture> eventPictures;

	// Soft delete
	@Column(name = "deleted")
	private boolean deleted = false;

	@Builder
	public Event(
			Date startDate,
			Date endDate,
			String title,
			String location,
			String description,
			String keyWords,
			EventCategory category) {
		setStartDate(startDate);
		setEndDate(endDate);
		setTitle(title);
		setLocation(location);
		setDescription(description);
		setKeyWords(keyWords);
		setCategory(category);
	}

}