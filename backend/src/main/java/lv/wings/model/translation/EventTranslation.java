package lv.wings.model.translation;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import lv.wings.enums.LocaleCode;
import lv.wings.model.base.LocalableEntity;
import lv.wings.model.entity.Event;

@Entity
@Table(name = "event_translations")
@NoArgsConstructor
@Data
public class EventTranslation extends LocalableEntity<Event> {

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
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
