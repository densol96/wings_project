package lv.wings.model.entity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lv.wings.model.base.TranslatableEntity;
import lv.wings.model.translation.EventCategoryTranslation;

@Entity
@Table(name = "event_categories")
@NoArgsConstructor
@Getter
@Setter
public class EventCategory extends TranslatableEntity<EventCategoryTranslation> {

	@OneToMany(mappedBy = "category")
	private List<Event> events = new ArrayList<>();
}
