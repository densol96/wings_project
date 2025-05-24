package lv.wings.model.base;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;

import lombok.Data;
import lombok.NoArgsConstructor;

import lv.wings.model.interfaces.HasImages;
import lv.wings.model.interfaces.Imageable;
import lv.wings.model.interfaces.Localable;

@MappedSuperclass
@Data
@NoArgsConstructor
public abstract class ImageableEntity<L extends Localable, O extends HasImages<?>> extends TranslatableEntity<L> implements Imageable {
    @ManyToOne
    @JoinColumn(name = "has_image_id", nullable = false)
    private O owner;

    @Column(nullable = false, length = 200)
    private String src;

    @Column(nullable = false)
    private Integer position;

    protected ImageableEntity(O owner, String src) {
        this.owner = owner;
        this.src = src;
    }
}
