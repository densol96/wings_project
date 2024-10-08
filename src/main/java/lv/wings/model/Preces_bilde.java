package lv.wings.model;

import java.time.LocalDateTime;

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

@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name="PrecesBildeTable")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Preces_bilde {

	@Id
	@Column(name = "P_b_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Setter(value = AccessLevel.NONE)
	private int p_b_id;
	
	//saite uz preci
	@ManyToOne
	@JoinColumn(name="Prece_id")
	private Prece prece;
	
	
	@Column(name = "Bilde")
	private String bilde;
	
	@Column(name = "Apraksts")
	@NotNull
	@Size(min = 4, max = 150)
	private String apraksts;
	
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
	
	public Preces_bilde(String bilde, String apraksts, Prece prece) {
		setBilde(bilde);
		setApraksts(apraksts);
		setPrece(prece);
	}
	
}
