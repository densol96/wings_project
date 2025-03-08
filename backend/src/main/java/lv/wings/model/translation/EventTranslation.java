package lv.wings.model.translation;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lv.wings.enums.LocaleCode;
import lv.wings.model.Event;

@Entity
@Table(name = "event_translations")
@NoArgsConstructor
@Data
public class EventTranslation implements Localable {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LocaleCode locale;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    private String location;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @Builder
    public EventTranslation(
            LocaleCode locale,
            String title,
            String location,
            String description) {
        setLocale(locale);
        setTitle(title);
        setLocation(location);
        setDescription(description);
    }
}