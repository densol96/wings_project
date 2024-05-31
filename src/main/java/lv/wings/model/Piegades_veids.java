package lv.wings.model;

import java.util.Collection;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name = "Piegades_Veids_Table")
@Entity
public class Piegades_veids {
	
	@Id
	@Column(name = "PV_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Setter(value = AccessLevel.NONE)
	private int pv_ID;
	
	
	//TODO iespejams papildus anotacijas
	@Column(name = "Nosaukums")
	private String nosaukums;
	
	
	@Column(name = "Apraksts")
	private String apraksts;
	
	
	@OneToMany(mappedBy = "piegades_veids")
	@ToString.Exclude
	private Collection<Pirkums> pirkumi;
	
	
	public Piegades_veids(String nosaukums, String apraksts) {
		setNosaukums(nosaukums);
		setApraksts(apraksts);
	}
}
