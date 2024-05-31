package lv.wings.model;

import java.time.LocalDateTime;
import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

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
import jakarta.validation.constraints.Pattern;
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
@Table(name = "pasakumi")
@ToString
@Entity
public class Pasakums {
	@Column(name = "idpa")
	@Id
	@Setter(value = AccessLevel.NONE)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int idpa;

	@NotNull
	@Column(name = "sakumadatums")
	private LocalDateTime sakumaDatums; /// sukumaDatums also includes time.

	@NotNull
	@Column(name = "beigudatums")
	private LocalDateTime beiguDatums;

	@NotNull
	@Column(name = "nosaukums")
	@Size(min = 5, max = 300, message = "Nosaukums nedrīkst saturēt mazāk par 5 vai vairāk par 300 rakstzīmēm!")
	private String nosaukums;

	@NotNull
	@Column(name = "vieta")
	@Size(min = 2, max = 200, message = "Vieta nedrīkst saturēt mazāk par 2 vai vairāk par 200 rakstzīmēm!")
	private String vieta;

	@NotNull
	@Column(name = "apraksts")
	@Size(min = 0, max = 3000, message = "Aprakstā par daudz rakstzīmju! (0-3000)")
	private String apraksts;

	
	/// Need to validate this
	@NotNull
	@Column(name = "keyWords")
	private String keyWords;
	
	
	
	/////// Saites //////
	@OneToMany(mappedBy = "pasakums")
	private Collection<PasakumaBilde> pasakumaBilde;
	
	@ManyToOne
	@JoinColumn(name = "idpako")
	private PasakumaKomentars pasakumaKomentars;
	
	@ManyToOne
	@JoinColumn(name = "idpaka")
	private PasakumaKategorija pasakumaKategorija;
	
	

	public Pasakums(LocalDateTime sakumaDatums, LocalDateTime beiguDatums, String nosaukums, String vieta,
			String apraksts, String keyWords, PasakumaKomentars pasakumaKomentars, PasakumaKategorija pasakumaKategorija) {
		setSakumaDatums(sakumaDatums);
		setBeiguDatums(beiguDatums);
		setNosaukums(nosaukums);
		setVieta(vieta);
		setApraksts(apraksts);
		setKeyWords(keyWords);
		setPasakumaKomentars(pasakumaKomentars);
		setPasakumaKategorija(pasakumaKategorija);
	}

}
