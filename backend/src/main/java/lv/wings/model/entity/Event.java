package lv.wings.model.entity;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import lv.wings.model.translation.EventTranslation;
import lv.wings.model.base.TranslatableEntity;

@Entity
@Table(name = "events")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@Data
@SQLDelete(sql = "UPDATE event SET deleted = true WHERE event_id=?")
@Where(clause = "deleted=false")
public class Event extends TranslatableEntity<EventTranslation> {

	private LocalDate startDate;

	private LocalDate endDate;

	@ManyToOne
	@JoinColumn(name = "category_id", nullable = false)
	private EventCategory category;

	@OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<EventPicture> images;

	// Soft delete
	@Column(name = "deleted")
	private boolean deleted = false;

	@Builder
	public Event(LocalDate startDate, LocalDate endDate, EventCategory category, List<EventTranslation> translations) {
		setStartDate(startDate);
		setEndDate(endDate);
		setCategory(category);
	}
}