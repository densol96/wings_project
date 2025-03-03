package lv.wings.model.security;

import java.util.Collection;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@ToString
@Entity
@Table(name="WingsAuthority")
public class MyAuthority {
	
	@Id
	@Column(name = "AuthorityId")
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Setter(value = AccessLevel.NONE)
	private int authorityId;
	
	@Column(name = "Title")
	@Pattern(regexp = "[A-Z]{4,7}")
	private String title;
	
	@OneToMany(mappedBy = "authority")
	@ToString.Exclude
	private Collection<MyUser> users;

	public MyAuthority(@Pattern(regexp = "[A-Z]{4,7}") String title) {
		this.title = title;
	}
	
	
	
}
