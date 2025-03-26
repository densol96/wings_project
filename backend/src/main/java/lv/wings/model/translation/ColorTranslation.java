package lv.wings.model.translation;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lv.wings.enums.LocaleCode;
import lv.wings.model.base.LocalableEntity;
import lv.wings.model.entity.Color;

@Entity
@Table(name = "color_translations")
@NoArgsConstructor
@Data
public class ColorTranslation extends LocalableEntity<Color> {
    @Column(nullable = false, unique = true)
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
