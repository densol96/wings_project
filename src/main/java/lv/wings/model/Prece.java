package lv.wings.model;

import java.util.Collection;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
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
@Table(name="PreceTable")
@Entity
public class Prece {
	
	@Id
	@Column(name = "Prece_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Setter(value = AccessLevel.NONE)
	private int prece_id;
	
	//saite no kategorijas
	@ManyToOne
	@JoinColumn(name="Kategorijas_id")
	private Kategorijas kategorijas;
	
	//saite uz bildi
	@OneToMany(mappedBy = "prece")
	@ToString.Exclude
	private Collection<Preces_bilde> preces_bildes;
	
	//saite uz pirkuma_elementu
	@OneToMany(mappedBy = "prece")
	@ToString.Exclude
	private Collection<Pirkuma_elements> pirkuma_elementi;
	
	
	@Column(name = "Nosaukums")
	@NotNull
	@Size(min = 4, max = 50)
	@Pattern(regexp = "[A-ZĒŪĪĻĶĢŠĀŽČŅa-zēūīļķģšāžčņ ]+", message = "Only letters and space are allowed")
	private String nosaukums;
	
	@Column(name = "Apraksts")
	@NotNull
	@Size(min = 4, max = 150)
	private String apraksts;
	

	@Column(name = "Cena")
	@Min(0)
	private float cena;
	
	@Column(name = "Daudzums")
	@Min(0)
	private int daudzums;
	
	public Prece(String apraksts, float cena, int daudzums) {
		setApraksts(apraksts);
		setCena(cena);
		setDaudzums(daudzums);
	}
	

}
