package lv.wings.auditing;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import lv.wings.model.security.MyUser;

public class ApplicationAuditAware implements AuditorAware<MyUser> {

	@Override
	public Optional<MyUser> getCurrentAuditor() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated()) {
			return Optional.empty();
		}
		return Optional.of((MyUser) authentication.getPrincipal());
	}

}
