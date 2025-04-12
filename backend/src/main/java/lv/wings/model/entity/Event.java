package lv.wings.model.entity;

import java.time.LocalDate;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import lv.wings.model.translation.EventTranslation;
import lv.wings.model.base.OwnerableEntity;

@Entity
@Table(name = "events")
@NoArgsConstructor
@Data
public class Event extends OwnerableEntity<EventTranslation, EventImage> {

	private LocalDate startDate;

	private LocalDate endDate;

	@ManyToOne
	@JoinColumn(name = "category_id", nullable = false)
	private EventCategory category;

	@Builder
	public Event(LocalDate startDate, LocalDate endDate, EventCategory category) {
		setStartDate(startDate);
		setEndDate(endDate);
		setCategory(category);
	}
}
