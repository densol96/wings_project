package lv.wings.model.entity;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lv.wings.model.base.TranslatableEntity;
import lv.wings.model.translation.ColorTranslation;

@Entity
@Table(name = "colors")
@Getter
@Setter
@NoArgsConstructor
public class Color extends TranslatableEntity<ColorTranslation> {

    @ManyToMany(mappedBy = "colors")
    private List<Product> products = new ArrayList<>();
}
