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
import lv.wings.model.entity.Event;

@Entity
@NoArgsConstructor
@Data
@Table(
        name = "event_translations",
        uniqueConstraints = @UniqueConstraint(columnNames = {"translatable_id", "locale"}))
public class EventTranslation extends LocalableEntity<Event> {

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, length = 1000)
    private String description;

    private String location;

    @Builder
    public EventTranslation(LocaleCode locale, Event event, String title, String location, String description) {
        super(locale, event);
        setTitle(title);
        setLocation(location);
        setDescription(description);
    }
}
