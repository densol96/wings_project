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
@Table(name = "Samaksas_Veids_Table")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Samaksas_veids {
	
	@Id
	@Column(name = "SVID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Setter(value = AccessLevel.NONE)
	private int svID;
	
	//TODO iespejams papildus anotacijas
	@Column(name = "Nosaukums")
	private String nosaukums;
		
		
	@Column(name = "Piezimes")
	private String piezimes;
		
	
	@OneToMany(mappedBy = "samaksasVeids")
	@ToString.Exclude
	private Collection<Pirkums> pirkumi;
	
	@CreatedDate
	@Column(nullable = false,updatable = false)
	private LocalDateTime createDate;
	
	@LastModifiedDate
	@Column(insertable = false)
	private LocalDateTime lastModified;
	
	@CreatedBy
	//@Column(nullable = false,updatable = false)
	private Integer createdBy;
	
	@LastModifiedBy
	@Column(insertable = false)
	private Integer lastModifiedBy;
		
	
	public Samaksas_veids(String nosaukums, String piezimes) {
		setNosaukums(nosaukums);
		setPiezimes(piezimes);
	}
	
}
