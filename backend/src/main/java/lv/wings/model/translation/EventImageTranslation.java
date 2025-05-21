package lv.wings.model.translation;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import lv.wings.enums.LocaleCode;
import lv.wings.model.base.ImageLocalableEntity;
import lv.wings.model.entity.EventImage;

@Entity
@NoArgsConstructor
@Data
@Table(
        name = "event_picture_translations",
        uniqueConstraints = @UniqueConstraint(columnNames = {"translatable_id", "locale"}))
public class EventImageTranslation extends ImageLocalableEntity<EventImage> {

    @Builder
    public EventImageTranslation(LocaleCode locale, EventImage image, String alt) {
        super(locale, image, alt);
    }
}
