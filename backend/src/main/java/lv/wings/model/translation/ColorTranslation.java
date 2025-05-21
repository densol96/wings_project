package lv.wings.model.translation;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lv.wings.enums.LocaleCode;
import lv.wings.model.base.LocalableEntity;
import lv.wings.model.entity.Color;

@Entity
@NoArgsConstructor
@Data
@Table(
        name = "color_translations",
        uniqueConstraints = @UniqueConstraint(columnNames = {"translatable_id", "locale"}))
public class ColorTranslation extends LocalableEntity<Color> {

    @Column(nullable = false, unique = true, length = 50)
    String name;

    @Builder
    public ColorTranslation(LocaleCode locale, Color color, String name) {
        super(locale, color);
        this.name = name;
    }

    @PrePersist
    @PreUpdate
    protected void lowercaseColorName() {
        name = name.toLowerCase();
    }
}
