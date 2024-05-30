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
	private String komentars;
	
	@NotNull
	@Column(name = "datums")
	private LocalDateTime datums;
	
	public PasakumaKomentars(String komentars, LocalDateTime datums) {
		setKomentars(komentars);
		setDatums(datums);
	}
}
