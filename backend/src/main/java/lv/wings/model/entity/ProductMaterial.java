package lv.wings.model.entity;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lv.wings.model.base.AuditableEntityExtended;

@Entity
@Table(
        name = "product_material",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"product_id", "material_id"})})
@NoArgsConstructor
@Data
@EntityListeners(AuditingEntityListener.class)
public class ProductMaterial extends AuditableEntityExtended {

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "material_id", nullable = false)
    private Material material;

    @Column(nullable = false)
    private Integer percentage; // 0 -100

    @Builder
    public ProductMaterial(Product product, Material material, Integer percentage) {
        this.product = product;
        this.material = material;
        this.percentage = percentage;
    }
}
