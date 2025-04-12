package lv.wings.model.entity;

import java.util.Collection;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

import lv.wings.model.base.TranslatableEntity;
import lv.wings.model.translation.PaymentTypeTranslation;


@Entity
@Table(name = "payment_types")
@NoArgsConstructor
@Data
@SQLDelete(sql = "UPDATE Payment_Type SET deleted = true WHERE payment_type_id=?")
@Where(clause = "deleted=false")
public class PaymentType extends TranslatableEntity<PaymentTypeTranslation> {

	@OneToMany(mappedBy = "paymentType")
	private Collection<Purchase> purchases;

	@Column(name = "deleted")
	private boolean deleted = false;

}
