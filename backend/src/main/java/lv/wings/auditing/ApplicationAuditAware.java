package lv.wings.auditing;

import java.util.Optional;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lv.wings.config.security.MyUserDetails;
import lv.wings.model.security.User;
import lv.wings.repo.UserRepository;

@Component
@RequiredArgsConstructor
public class ApplicationAuditAware implements AuditorAware<User> {

	private User systemUser;
	private final UserRepository userRepo;

	@Override
	public Optional<User> getCurrentAuditor() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null
				|| !authentication.isAuthenticated()
				|| authentication.getPrincipal() instanceof String) { // mind anonymousUser
			// if (systemUser == null) {
			// systemUser = userRepo.findByUsername("system").orElseGet(null);
			// }
			return Optional.ofNullable(systemUser);
		}

		return Optional.of(((MyUserDetails) authentication.getPrincipal()).getUser());
	}
}
