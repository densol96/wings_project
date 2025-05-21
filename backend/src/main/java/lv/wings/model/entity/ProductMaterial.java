package lv.wings.model.entity;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import lv.wings.model.base.AuditableEntityExtended;

@Entity
@Table(
        name = "product_material",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"product_id", "material_id"})})
@NoArgsConstructor
@Getter
@Setter
@SQLDelete(sql = "UPDATE product_material SET deleted = true WHERE id=?")
public class ProductMaterial extends AuditableEntityExtended {

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "material_id", nullable = false)
    private Material material;

    @Column(nullable = false)
    private Integer percentage; // 0 - 100

    private boolean deleted = false;

    @Builder
    public ProductMaterial(Product product, Material material, Integer percentage) {
        this.product = product;
        this.material = material;
        this.percentage = percentage;
    }
}
