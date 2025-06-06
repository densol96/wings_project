package lv.wings.config.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lv.wings.model.security.User;

@Getter
public class MyUserDetails implements UserDetails {

	private User user;

	public MyUserDetails(User user) {
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<GrantedAuthority> authorities = new HashSet<>();
		user.getRoles()
				.forEach(role -> {
					role.getPermissions().forEach(permission -> authorities.add(new SimpleGrantedAuthority(permission.getName().name())));
					authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
				});
		return authorities;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonLocked() {
		return !user.isAccountLocked();
	}

	@Override
	public boolean isEnabled() {
		if (user.isSystemUser()) {
			return false;
		}
		return !user.isAccountBanned();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

}
