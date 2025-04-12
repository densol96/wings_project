package lv.wings.model.entity;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lv.wings.model.base.TranslatableEntity;
import lv.wings.model.translation.ColorTranslation;

@Entity
@Table(name = "colors")
@Data
@NoArgsConstructor
public class Color extends TranslatableEntity<ColorTranslation> {

    @ManyToMany(mappedBy = "colors")
    private List<Product> products = new ArrayList<>();
}
