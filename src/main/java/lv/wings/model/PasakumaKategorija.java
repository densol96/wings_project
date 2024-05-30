package lv.wings.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//TODO: Need to check inputs or change something and create table relations if needed  NOT FINISHED!
@Setter
@Getter
@NoArgsConstructor
@Table(name = "pasakumuKategorijas")
@ToString
@Entity
public class PasakumaKategorija {
	@Column(name = "idpaka")
	@Id
	@Setter(value = AccessLevel.NONE)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int idpaka;
	
	@NotNull
	@Column(name = "nosaukums")
	private String nosaukums;
	
	public PasakumaKategorija(String nosaukums) {
		setNosaukums(nosaukums);
	}
}
