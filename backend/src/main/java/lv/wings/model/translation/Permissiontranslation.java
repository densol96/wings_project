package lv.wings.model.translation;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lv.wings.enums.LocaleCode;
import lv.wings.model.base.LocalableEntity;
import lv.wings.model.entity.Color;
import lv.wings.model.security.Permission;

@Entity
@Table(name = "permission_translations")
@NoArgsConstructor
@Data
public class Permissiontranslation extends LocalableEntity<Permission> {

    @Column(nullable = false, unique = true)
    String name;

    @Builder
    public Permissiontranslation(LocaleCode locale, Permission color, String name) {
        super(locale, color);
        this.name = name;
    }
}
