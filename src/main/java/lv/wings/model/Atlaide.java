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
	private float atlaidesApmers;

	@NotNull
	@Column(name = "sakumaDatums")
	private LocalDateTime sakumaDatums;
	
	@NotNull
	@Column(name = "beiguDatums")
	private LocalDateTime beiguDatums;

	@NotNull
	@Column(name = "apraksts")
	private String apraksts;
	
	
}
