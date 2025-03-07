package lv.wings.model.security;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
@Table(name = "WingsUser")
public class MyUser {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Setter(value = AccessLevel.NONE)
	private Integer userId;

	@Column(name = "Username")
	private String username;

	@Column(name = "Password")
	private String password;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "AuthorityId")
	private MyAuthority authority;

	public MyUser(String username, String password, MyAuthority authority) {
		this.username = username;
		this.password = password;
		this.authority = authority;
	}

	// necessary for ApplicationAuditAware
	public Integer getUserId() {
		return userId;
	}
}
