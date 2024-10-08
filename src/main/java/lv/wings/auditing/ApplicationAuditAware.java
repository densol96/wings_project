//package lv.wings.auditing;
//
//import java.util.Optional;
//
//import org.springframework.security.core.Authentication;
//import org.springframework.data.domain.AuditorAware;
//
//public class ApplicationAuditAware implements AuditorAware<Integer>{
//
//	@Override
//	public Optional<Integer> getCurrentAuditor() {
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		if(authentication == null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken) {
//			return Optional.empty();
//		}
//		
//		User userPrincipal = (User)authentication.getPrincipal();
//		return Optional.ofNullable(userPrincipal.getId());
//	}
//	
//
//}
