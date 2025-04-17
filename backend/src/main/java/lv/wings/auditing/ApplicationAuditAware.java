package lv.wings.auditing;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import lv.wings.model.security.MyUser;

public class ApplicationAuditAware implements AuditorAware<MyUser> {

	// @Override
	// public Optional<MyUser> getCurrentAuditor() {
	// Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	// if (authentication == null || !authentication.isAuthenticated()) {
	// return Optional.empty();
	// }
	// return Optional.of((MyUser) authentication.getPrincipal());
	// }

	@Override
	public Optional<MyUser> getCurrentAuditor() {

		return Optional.empty();
	}

}
