package lv.wings.model.translation;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import lv.wings.enums.LocaleCode;
import lv.wings.model.base.LocalableEntity;
import lv.wings.model.entity.EventPicture;

@Entity
@Table(name = "event_pricture_translations")
@NoArgsConstructor
@Data
public class EventPictureTranslation extends LocalableEntity<EventPicture> {

    @Column(nullable = false)
    private String alt;

    @Builder
    public EventPictureTranslation(LocaleCode locale, EventPicture picture, String alt) {
        super(locale, picture);
        setAlt(alt);
    }
}
