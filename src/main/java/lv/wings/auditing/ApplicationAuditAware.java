package lv.wings.auditing;

import java.util.Optional;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import lv.wings.model.security.MyUser;

import org.springframework.data.domain.AuditorAware;

public class ApplicationAuditAware implements AuditorAware<Integer>{

	@Override
	public Optional<Integer> getCurrentAuditor(){
		
		return Optional.ofNullable(SecurityContextHolder.getContext())
				.map(SecurityContext::getAuthentication)
				.filter(Authentication::isAuthenticated)
				.map(Authentication::getPrincipal)
				.filter(principal -> principal instanceof MyUser)
				.map(MyUser.class::cast)
				.map(MyUser::getUserId);
	
	}
	

}
