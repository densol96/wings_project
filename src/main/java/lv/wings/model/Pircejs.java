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
	@Column(name = "PircejsID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Setter(value = AccessLevel.NONE)
	private int idpirc;
	
	
	@Column(name = "Vards")
	@NotNull
	@Pattern(regexp = "[A-ZĒŪĪĻĶĢŠĀŽČŅa-zēūīļķģšāžčņ' ]+", message = "Tikai burti un atstarpes ir atlautas")
	@Size(max = 20, min = 2)
	private String vards;
	
	
	@Column(name = "Uzvards")
	@NotNull
	@Pattern(regexp = "[A-ZĒŪĪĻĶĢŠĀŽČŅa-zēūīļķģšāžčņ' ]+", message = "Tikai burti un atstarpes ir atlautas")
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
	@Column(name = "PersonasKods")
	@NotNull
	private String personasKods;
	
	
	//TODO REGEX
	@Column(name = "BankasNosaukums")
	@NotNull
	private String bankasNosaukums;
	
	
	//TODO REGEX
	//Mainigais int ?
	@Column(name = "BankasSwiftKods")
	@NotNull
	private String bankasSwiftKods;
	
	
	//TODO REGEX
	//Mainigais int ?
	@Column(name = "BankasKods")
	@NotNull
	private String bankasKods;
	
	
	public Pircejs(String vards, String uzvards, String epasts, String adrese, String personasKods,
			String bankasNosaukums, String bankasSwiftKods, String bankasKods) {
		setVards(vards);
		setUzvards(uzvards);
		setEpasts(epasts);
		setAdrese(adrese);
		setPersonasKods(personasKods);
		setBankasNosaukums(bankasNosaukums);
		setBankasSwiftKods(bankasSwiftKods);
		setBankasKods(bankasKods);
	}
	
	
}
