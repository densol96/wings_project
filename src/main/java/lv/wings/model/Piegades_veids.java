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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name = "Piegades_Veids_Table")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Piegades_veids {
	
	@Id
	@Column(name = "PVID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Setter(value = AccessLevel.NONE)
	private int pvID;
	
	
	//TODO iespejams papildus anotacijas
	@Column(name = "Nosaukums")
	private String nosaukums;
	
	
	@Column(name = "Apraksts")
	private String apraksts;
	
	
	@OneToMany(mappedBy = "piegadesVeids")
	@ToString.Exclude
	private Collection<Pirkums> pirkumi;
	
	@CreatedDate
	@Column(nullable = false,updatable = false)
	private LocalDateTime createDate;
	
	@LastModifiedDate
	@Column(insertable = false)
	private LocalDateTime lastModified;
	
	@CreatedBy
	@Column(updatable = false)
	private Integer createdBy;
	
	@LastModifiedBy
	@Column(insertable = false)
	private Integer lastModifiedBy;
	
	
	public Piegades_veids(String nosaukums, String apraksts) {
		setNosaukums(nosaukums);
		setApraksts(apraksts);
	}
}
