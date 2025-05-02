package lv.wings.auditing;

import java.util.Optional;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import lv.wings.config.security.MyUserDetails;
import lv.wings.model.security.User;

public class ApplicationAuditAware implements AuditorAware<User> {

	// @Override
	// public Optional<User> getCurrentAuditor() {
	// Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	// if (authentication == null || !authentication.isAuthenticated()) {
	// return Optional.empty();
	// }
	// return Optional.of(((MyUserDetails) authentication.getPrincipal()).getUser());
	// }

	@Override
	public Optional<User> getCurrentAuditor() {
		return Optional.empty();
	}

}
