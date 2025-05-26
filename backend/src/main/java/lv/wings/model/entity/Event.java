package lv.wings.model.entity;

import java.time.LocalDate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lv.wings.model.translation.EventTranslation;
import lv.wings.model.base.OwnerableEntity;

@Entity
@Table(name = "events")
@NoArgsConstructor
@Getter
@Setter
public class Event extends OwnerableEntity<EventTranslation, EventImage> {

	private LocalDate startDate;

	private LocalDate endDate;

	@Builder
	public Event(LocalDate startDate, LocalDate endDate) {
		this.startDate = startDate;
		this.endDate = endDate;
	}
}
