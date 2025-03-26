package lv.wings.model.entity;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lv.wings.model.base.TranslatableEntity;
import lv.wings.model.translation.MaterialTranslation;

@Entity
@Table(name = "materials")
@Data
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Material extends TranslatableEntity<MaterialTranslation> {

    @OneToMany(mappedBy = "material")
    private List<ProductMaterial> madeProducts = new ArrayList<>();
}
