package lv.wings.model;

import java.time.LocalDateTime;
import java.util.Collection;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
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
@EntityListeners(AuditingEntityListener.class)
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
	
	
	@CreatedDate
	@Column(nullable = false,updatable = false)
	private LocalDateTime createDate;
	
	@LastModifiedDate
	@Column(insertable = false)
	private LocalDateTime lastModified;
	
	@CreatedBy
	//@Column(updatable = false)
	private Integer createdBy;
	
	@LastModifiedBy
	@Column(insertable = false)
	private Integer lastModifiedBy;
	
	public Prece(String nosaukums,String apraksts, float cena, int daudzums, Kategorijas kategorija) {
		setNosaukums(nosaukums);
		setApraksts(apraksts);
		setCena(cena);
		setDaudzums(daudzums);
		setKategorijas(kategorija);
	}
	

}
