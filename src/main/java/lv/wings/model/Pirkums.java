package lv.wings.model;

import java.time.LocalDateTime;
import java.util.Collection;

import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name = "Pirkums_Table")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Pirkums {
	
	@Id
	@Column(name = "PirkumsID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Setter(value = AccessLevel.NONE)
	private int pirkumsID;
	

	@ManyToOne
	@JoinColumn(name = "PVID")
	private Piegades_veids piegadesVeids;
	
	
	@ManyToOne
	@JoinColumn(name = "SVID")
	private Samaksas_veids samaksasVeids;
	
	
	@ManyToOne
	@JoinColumn(name = "PircejsID")
	private Pircejs pircejs;
	
	
	@Column(name = "PasutijumaDatums")
	private LocalDateTime pasutijumaDatums;
	
	
	@Column(name = "PiegadesDetalas")
	private String piegadesDetalas;
	

	@OneToMany(mappedBy = "pirkums")
	@ToString.Exclude
	private Collection<Pirkuma_elements> pirkumaElementi;
	
	@LastModifiedDate
	@Column(insertable = false)
	private LocalDateTime lastModifiedByAdmin;
	
	@LastModifiedBy
	@Column(insertable = false)
	private Integer lastModifiedBy;

	
	public Pirkums(Piegades_veids piegadesVeids, Samaksas_veids samaksasVeids, Pircejs pircejs, LocalDateTime pasutijumaDatums, String piegadesDetalas) {
		setPiegadesVeids(piegadesVeids);
		setSamaksasVeids(samaksasVeids);
		setPircejs(pircejs);
		setPasutijumaDatums(pasutijumaDatums);
		setPiegadesDetalas(piegadesDetalas);
	}

}
