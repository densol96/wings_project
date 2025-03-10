package lv.wings.model.translation;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lv.wings.enums.LocaleCode;
import lv.wings.model.entity.Event;
import lv.wings.model.interfaces.Localable;

@Entity
@Table(name = "event_translations")
@NoArgsConstructor
@Data
public class EventTranslation implements Localable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(value = AccessLevel.NONE)
    private Integer id;

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