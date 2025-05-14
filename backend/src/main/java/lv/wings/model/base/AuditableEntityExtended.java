package lv.wings.model.base;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;

import lombok.Data;

import lv.wings.model.interfaces.ExtendedAuditable;
import lv.wings.model.security.User;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Data
public abstract class AuditableEntityExtended extends AuditableEntity implements ExtendedAuditable {
    @CreatedBy
    @ManyToOne
    @JoinColumn(nullable = false, updatable = false)
    private User createdBy;

}
