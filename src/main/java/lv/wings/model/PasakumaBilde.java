package lv.wings.model;

import java.time.LocalDateTime;

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
@Table(name = "pasakumaBildes")
@ToString
@Entity
public class PasakumaBilde {
	@Column(name = "idpab")
	@Id
	@Setter(value = AccessLevel.NONE)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int idpab;
	
	
	/// image changed to image reference (link - string)
	@NotNull
	@Column(name = "atsauceUzBildi")
	private String atsauceUzBildi;
	
	@NotNull
	@Column(name = "nosaukums")
	private String nosaukums;

	@NotNull
	@Column(name = "apraksts")
	private String apraksts;
	
	public PasakumaBilde(String atsauceUzBildi, String nosaukums, String apraksts) {
		setAtsauceUzBildi(atsauceUzBildi);
		setNosaukums(nosaukums);
		setApraksts(apraksts);
	}

}
