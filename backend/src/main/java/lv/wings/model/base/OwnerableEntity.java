package lv.wings.model.base;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.OneToMany;

import lombok.Data;
import lombok.NoArgsConstructor;
import lv.wings.model.interfaces.HasImages;
import lv.wings.model.interfaces.Imageable;
import lv.wings.model.interfaces.Localable;

@MappedSuperclass
@Data
@NoArgsConstructor
public abstract class OwnerableEntity<L extends Localable, I extends Imageable> extends TranslatableEntity<L> implements HasImages<I> {
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<I> images = new ArrayList<>();
}
