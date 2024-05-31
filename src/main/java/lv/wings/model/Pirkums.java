package lv.wings.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
public class Pirkums {
	
	@Id
	@Column(name = "Pirkums_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Setter(value = AccessLevel.NONE)
	private int pirkums_ID;
	

	@OneToMany
	@JoinColumn(name = "PV_ID")
	private Piegades_veids piegades_veids;
	
	
	@OneToMany
	@JoinColumn(name = "SV_ID")
	private Samaksas_veids samaksas_veids;
	
	
	@ManyToOne
	@JoinColumn(name = "Pircejs_ID")
	private Pircejs pircejs;
	
	
	@Column(name = "Pasutijuma_datums")
	@NotNull
	private LocalDateTime pasutijuma_datums;
	
	
	@Column(name = "Piegades_detalas")
	private String piegades_detalas;

}
