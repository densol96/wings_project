package lv.wings.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
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
@Table(name="PrecesBildeTable")
@Entity
public class Preces_bilde {

	@Id
	@Column(name = "P_b_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Setter(value = AccessLevel.NONE)
	private int p_b_id;
	
	
	//TODO sis naks no preces tabulas
	//private int prece_id;
	
	@Column(name = "Bilde")
	private String bilde;
	
	@Column(name = "Apraksts")
	@NotNull
	@Size(min = 4, max = 150)
	private String apraksts;
}
