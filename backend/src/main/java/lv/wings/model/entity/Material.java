package lv.wings.model.entity;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import lv.wings.model.base.TranslatableEntity;
import lv.wings.model.translation.MaterialTranslation;

@Entity
@Table(name = "materials")
@Getter
@Setter
@NoArgsConstructor
public class Material extends TranslatableEntity<MaterialTranslation> {

    @OneToMany(mappedBy = "material", cascade = CascadeType.ALL)
    private List<ProductMaterial> madeProducts = new ArrayList<>();
}
