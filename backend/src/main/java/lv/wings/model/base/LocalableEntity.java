package lv.wings.model.base;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import lv.wings.enums.LocaleCode;
import lv.wings.model.interfaces.Localable;
import lv.wings.model.interfaces.Translatable;

@MappedSuperclass
@NoArgsConstructor
@Data
public abstract class LocalableEntity<T extends Translatable> implements Localable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(value = AccessLevel.NONE)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LocaleCode locale;

    @ManyToOne
    @JoinColumn(name = "translatable_id", nullable = false)
    private T entity;

    protected LocalableEntity(LocaleCode locale, T entity) {
        this.locale = locale;
        this.entity = entity;
    }

}
