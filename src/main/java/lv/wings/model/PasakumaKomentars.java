package lv.wings.model;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Table(name = "pasakumuKomentari")
@ToString
@Entity
public class PasakumaKomentars {
	@Column(name = "idpako")
	@Id
	@Setter(value = AccessLevel.NONE)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int idpako;

	@NotNull
	@Column(name = "komentars")
	@Size(min = 2, max = 250, message = "Pasākuma komentārs nedrīkst saturēt mazāk par 2 vai vairāk par 250 rakstzīmēm!")
	private String komentars;

	@NotNull
	@Column(name = "datums")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date datums;

	@ManyToOne
	@JoinColumn(name = "idpa")
	private Pasakums pasakums;

	public PasakumaKomentars(String komentars, Date datums, Pasakums pasakums) {
		setKomentars(komentars);
		setDatums(datums);
		setPasakums(pasakums);
	}
}