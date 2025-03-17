package lv.wings.model.translation;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import lv.wings.enums.LocaleCode;
import lv.wings.model.base.ImageLocalableEntity;
import lv.wings.model.entity.EventImage;

@Entity
@Table(name = "event_picture_translations")
@NoArgsConstructor
@Data
public class EventImageTranslation extends ImageLocalableEntity<EventImage> {

    @Builder
    public EventImageTranslation(LocaleCode locale, EventImage image, String alt) {
        super(locale, image, alt);
    }
}
