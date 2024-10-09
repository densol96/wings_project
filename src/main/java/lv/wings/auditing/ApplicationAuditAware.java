package lv.wings.auditing;

import java.util.Optional;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import lv.wings.config.MyUserDetails;
import lv.wings.model.security.MyUser;

import org.springframework.data.domain.AuditorAware;

public class ApplicationAuditAware implements AuditorAware<Integer>{

	@Override
	public Optional<Integer> getCurrentAuditor(){
				
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

	    if (authentication == null || !authentication.isAuthenticated() 
	            || authentication instanceof AnonymousAuthenticationToken) {
	        return Optional.empty();
	    }

	    Object principal = authentication.getPrincipal();
	    
	    if (principal instanceof MyUserDetails) {
	        MyUserDetails userDetails = (MyUserDetails) principal;
	        MyUser myUser = userDetails.getMyUser(); // Get MyUser from MyUserDetails
	        return Optional.ofNullable(myUser.getUserId());
	    }

	    return Optional.empty();
	    
	
	}
	

}
