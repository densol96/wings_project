package lv.wings.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name="PirkumaElementsTable")
@Entity
public class Pirkuma_elements {

	@Id
	@Column(name = "P_e_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Setter(value = AccessLevel.NONE)
	private int p_e_id;
	
	//TODO te nak pirkums ID MARKUSS
	@ManyToOne
	@JoinColumn(name = "Pirkums_ID")
	private Pirkums pirkums;
	
	//saite uz preces
	@ManyToOne
	@JoinColumn(name = "Prece_id")
	private Prece prece;
	
	@Column(name = "Daudzums")
	@Min(1)
	private int daudzums;
	
	public Pirkuma_elements(Pirkums pirkums, Prece prece, int daudzums) {
		setPirkums(pirkums);
		setPrece(prece);
		setDaudzums(daudzums);
	}
}
