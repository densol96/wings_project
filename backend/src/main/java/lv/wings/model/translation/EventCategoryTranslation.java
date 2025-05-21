package lv.wings.model.translation;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lv.wings.enums.LocaleCode;
import lv.wings.model.base.LocalableEntity;
import lv.wings.model.entity.EventCategory;

@Entity
@NoArgsConstructor
@Data
@Table(
        name = "event_category_translations",
        uniqueConstraints = @UniqueConstraint(columnNames = {"translatable_id", "locale"}))
public class EventCategoryTranslation extends LocalableEntity<EventCategory> {

    @Column(unique = true, nullable = false)
    private String title;

    @Builder
    public EventCategoryTranslation(LocaleCode locale, EventCategory category, String title) {
        super(locale, category);
        setTitle(title);
    }
}
