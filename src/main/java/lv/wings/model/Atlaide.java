package lv.wings.model;

import java.time.LocalDateTime;
import java.util.Date;


import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
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
@EntityListeners(AuditingEntityListener.class)
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
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date sakumaDatums;
	
	@NotNull
	@Column(name = "beiguDatums")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date beiguDatums;

	@NotNull
	@Column(name = "apraksts")
	@Size(min = 0, max = 3000, message = "Aprakstā par daudz rakstzīmju! (0-3000)")
	private String apraksts;
	
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
	
	public Atlaide(int atlaidesApmers, Date sakumaDatums, Date beiguDatums, String apraksts) {
		setAtlaidesApmers(atlaidesApmers);
		setSakumaDatums(sakumaDatums);
		setBeiguDatums(beiguDatums);
		setApraksts(apraksts);
	}
	
	
}