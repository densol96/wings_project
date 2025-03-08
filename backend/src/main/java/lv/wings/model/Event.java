package lv.wings.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import lv.wings.model.translation.EventTranslation;
import lv.wings.model.translation.Localable;
import lv.wings.model.translation.Translatable;

@Entity
@Table(name = "events_lv")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@Data
@SQLDelete(sql = "UPDATE event SET deleted = true WHERE event_id=?")
@Where(clause = "deleted=false")
public class Event extends AuditableEntity implements Translatable {

	private LocalDate startDate;

	private LocalDate endDate;

	@ManyToOne
	@JsonManagedReference
	@JoinColumn(name = "category_id", nullable = false)
	private EventCategory category;

	@OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	private List<EventPicture> eventPictures;

	@OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
	@Getter(AccessLevel.NONE)
	private List<EventTranslation> translations;

	// Soft delete
	@Column(name = "deleted")
	private boolean deleted = false;

	@Builder
	public Event(
			LocalDate startDate,
			LocalDate endDate,
			String title,
			String location,
			String description,
			String keyWords,
			EventCategory category) {
		setStartDate(startDate);
		setEndDate(endDate);
		setCategory(category);
	}

	@Override
	public List<Localable> getTranslations() {
		return translations.stream().map(tr -> (Localable) tr).toList();
	}
}