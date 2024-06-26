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
@Table(name = "Samaksas_Veids_Table")
@Entity
public class Samaksas_veids {
	
	@Id
	@Column(name = "SVID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Setter(value = AccessLevel.NONE)
	private int svID;
	
	//TODO iespejams papildus anotacijas
	@Column(name = "Nosaukums")
	private String nosaukums;
		
		
	@Column(name = "Piezimes")
	private String piezimes;
		
	
	@OneToMany(mappedBy = "samaksasVeids")
	@ToString.Exclude
	private Collection<Pirkums> pirkumi;
		
	
	public Samaksas_veids(String nosaukums, String piezimes) {
		setNosaukums(nosaukums);
		setPiezimes(piezimes);
	}
	
}
