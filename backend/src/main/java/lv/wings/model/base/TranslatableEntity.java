package lv.wings.model.base;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.OneToMany;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lv.wings.model.interfaces.Localable;
import lv.wings.model.interfaces.Translatable;

@MappedSuperclass
@NoArgsConstructor
@Setter
public abstract class TranslatableEntity<L extends Localable> extends AuditableEntityExtended implements Translatable {

    @OneToMany(mappedBy = "entity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<L> translations = new ArrayList<>();

    @Override
    public List<Localable> getTranslations() {
        return translations.stream().map(tr -> (Localable) tr).toList();
    }
}
