package lv.wings.model.translation;

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
import lv.wings.model.entity.Material;

@Entity
@Table(name = "material_translations")
@NoArgsConstructor
@Data
public class MaterialTranslation extends LocalableEntity<Material> {

    @Column(nullable = false, unique = true)
    String name;

    @Builder
    public MaterialTranslation(LocaleCode locale, Material material, String name) {
        super(locale, material);
        this.name = name;
    }

    @PrePersist
    @PreUpdate
    protected void lowercaseMaterialName() {
        name = name.toLowerCase();
    }
}
