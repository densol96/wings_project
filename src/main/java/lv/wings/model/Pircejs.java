package lv.wings.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name = "Pircejs_Table")
@Entity
public class Pircejs {

	@Id
	@Column(name = "Pircejs_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Setter(value = AccessLevel.NONE)
	private int id_pirc;
	
	
	@Column(name = "Vards")
	@NotNull
	@Pattern(regexp = "[A-ZĒŪĪĻĶĢŠĀŽČŅa-zēūīļķģšāžčņ ]+", message = "Tikai burti un atstarpes ir atlautas")
	@Size(max = 20, min = 2)
	private String vards;
	
	
	@Column(name = "Uzvards")
	@NotNull
	@Pattern(regexp = "[A-ZĒŪĪĻĶĢŠĀŽČŅa-zēūīļķģšāžčņ ]+", message = "Tikai burti un atstarpes ir atlautas")
	@Size(max = 20, min = 2)
	private String uzvards;
	
	
	//TODO REGEX
	@Column(name = "Epasts")
	@NotNull
	private String epasts;
	
	
	//Iespejams jaunu klasi
	@Column(name = "Adrese")
	@NotNull
	private String adrese;
	
	
	//TODO REGEX
	@Column(name = "Personas_kods")
	@NotNull
	private String personas_kods;
	
	
	//TODO REGEX
	@Column(name = "Bankas_nosaukums")
	@NotNull
	private String bankas_nosaukums;
	
	
	//TODO REGEX
	//Mainigais int ?
	@Column(name = "Bankas_SWIFT_kods")
	@NotNull
	private String bankas_SWIFT_kods;
	
	
	//TODO REGEX
	//Mainigais int ?
	@Column(name = "Bankas_kods")
	@NotNull
	private String bankas_kods;
	
	
	public Pircejs(String vards, String uzvards, String epasts, String adrese, String personas_kods,
			String bankas_nosaukums, String bankas_swift_kods, String bankas_kods) {
		setVards(vards);
		setUzvards(uzvards);
		setEpasts(epasts);
		setAdrese(adrese);
		setPersonas_kods(personas_kods);
		setBankas_SWIFT_kods(bankas_swift_kods);
		setBankas_nosaukums(bankas_nosaukums);
		setBankas_kods(bankas_kods);
	}
	
	
}
