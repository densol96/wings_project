package lv.wings.model;

import java.time.LocalDateTime;

import org.springframework.cglib.core.Local;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
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
@Table(name = "atlaides")
@ToString
@Entity
public class Atlaide {
	@Column(name = "ida")
	@Id
	@Setter(value = AccessLevel.NONE)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int ida;
	
	
	@NotNull
	@Column(name = "atlaidesApmers")
	@PositiveOrZero(message = "Atlaides apmērs nevar būt negatīvs!")
	@Max(value = 100, message = "Atlaides apmērs nevar būt vairāk par 100%!")
	private int atlaidesApmers;

	@NotNull
	@Column(name = "sakumaDatums")
	private LocalDateTime sakumaDatums;
	
	@NotNull
	@Column(name = "beiguDatums")
	private LocalDateTime beiguDatums;

	@NotNull
	@Column(name = "apraksts")
	@Size(min = 0, max = 3000, message = "Aprakstā par daudz rakstzīmju! (0-3000)")
	private String apraksts;
	
	public Atlaide(int atlaidesApmers, LocalDateTime sakumaDatums, LocalDateTime beiguDatums, String apraksts) {
		setAtlaidesApmers(atlaidesApmers);
		setSakumaDatums(sakumaDatums);
		setBeiguDatums(beiguDatums);
		setApraksts(apraksts);
	}
	
	
}