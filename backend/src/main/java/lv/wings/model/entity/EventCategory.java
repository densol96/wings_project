package lv.wings.model.entity;

import java.util.List;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

import lv.wings.model.base.TranslatableEntity;
import lv.wings.model.translation.EventCategoryTranslation;

@Entity
@Table(name = "events_categories")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@Data
@SQLDelete(sql = "UPDATE event_category SET deleted = true WHERE event_category_id=?")
@Where(clause = "deleted=false")
public class EventCategory extends TranslatableEntity<EventCategoryTranslation> {

	@OneToMany(mappedBy = "category")
	private List<Event> events;

	@Column(name = "deleted")
	private boolean deleted = false;
}