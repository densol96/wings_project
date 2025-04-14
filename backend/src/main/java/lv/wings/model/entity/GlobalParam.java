package lv.wings.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lv.wings.model.base.AuditableEntityExtended;

@Entity
@Table(name = "global_params")
@Getter
@Setter
@NoArgsConstructor
public class GlobalParam extends AuditableEntityExtended {

    @Column(unique = true, nullable = false)
    private String title;

    @Column(unique = true, nullable = false)
    private String value;

    @Builder
    public GlobalParam(String title, String value) {
        this.title = title;
        this.value = value;
    }
}
