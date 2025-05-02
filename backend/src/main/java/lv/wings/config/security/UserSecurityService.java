package lv.wings.config.security;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import lv.wings.model.security.User;

@Component
public class UserSecurityService {

    public MyUserDetails getCurrentUserDetails() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            throw new AuthenticationCredentialsNotFoundException("No any authenticated user in the SecurityContextHolder");
        }

        Object principal = auth.getPrincipal();
        System.out.println(principal);
        if (principal instanceof MyUserDetails user) {
            return user;
        }
        throw new AuthenticationCredentialsNotFoundException("Invalid authentication in CurrentUserService.getCurrentUser");
    }

    public Set<String> getUserAuthorities(User user) {
        Set<String> allAuthorities = new HashSet<>();
        user.getRoles()
                .forEach(role -> {
                    role.getPermissions().forEach(permission -> allAuthorities.add(permission.getName().name()));
                    allAuthorities.add("ROLE_" + role.getName());
                });
        return allAuthorities;
    }
}
