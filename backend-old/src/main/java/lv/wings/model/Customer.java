package lv.wings.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
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
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
@Table(name = "Customer")
@Entity
@EntityListeners(AuditingEntityListener.class)
@SQLDelete(sql = "UPDATE Customer SET deleted = true WHERE customer_id=?")
@Where(clause = "deleted=false")
public class Customer {

	@Id
	@Column(name = "customer_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Setter(value = AccessLevel.NONE)
	private int customer_id;
	
	
	@Column(name = "name")
	@NotNull
	@Pattern(regexp = "[A-ZĒŪĪĻĶĢŠĀŽČŅa-zēūīļķģšāžčņ' ]+", message = "Tikai burti un atstarpes ir atlautas")
	@Size(max = 20, min = 2)
	private String name;
	
	
	@Column(name = "surname")
	@NotNull
	@Pattern(regexp = "[A-ZĒŪĪĻĶĢŠĀŽČŅa-zēūīļķģšāžčņ' ]+", message = "Tikai burti un atstarpes ir atlautas")
	@Size(max = 20, min = 2)
	private String surname;
	
	
	//TODO REGEX
	@Column(name = "email")
	@NotNull
	private String email;
	
	
	//Iespejams jaunu klasi
	@Column(name = "adress")
	@NotNull
	private String adress;

	
	@CreatedDate
	@Column(nullable = false, updatable = false)
	private LocalDateTime createDate;
	
	@LastModifiedDate
	@Column(insertable = false)
	private LocalDateTime lastModifiedByAdmin;
	
	@LastModifiedBy
	@Column(insertable = false)
	private Integer lastModifiedBy;

	//Soft delete
	@Column(name = "deleted")
	private boolean deleted = false;
	
	
	public Customer(String name, String surname, String email, String adress) {
		setName(name);
		setSurname(surname);
		setEmail(email);
		setAdress(adress);
	}
	
	
}
