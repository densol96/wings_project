package lv.wings.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
	@Size(min = 2, max = 200, message = "Kategorijas nosaukums nedrīkst saturēt mazāk par 1 vai vairāk par 100 rakstzīmēm!")
	private String nosaukums;

	@NotNull
	@Column(name = "apraksts")
	@Size(min = 0, max = 3000, message = "Aprakstā par daudz rakstzīmju! (0-3000)")
	private String apraksts;

	@ManyToOne
	@JoinColumn(name = "idpa")
	private Pasakums pasakums;

	public PasakumaBilde(String atsauceUzBildi, String nosaukums, String apraksts, Pasakums pasakums) {
		setAtsauceUzBildi(atsauceUzBildi);
		setNosaukums(nosaukums);
		setApraksts(apraksts);
		setPasakums(pasakums);
	}

}