package lv.wings.config.security;

import java.io.IOException;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lv.wings.enums.SecurityEventType;
import lv.wings.model.security.MyUser;
import lv.wings.model.security.User;
import lv.wings.service.JwtService;
import lv.wings.service.SecurityEventService;
import lv.wings.util.CustomValidator;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final MyUserDetailsService userDetailsService;
    private final SecurityEventService securirtyEventService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String token = authHeader.substring(7);
            String username = jwtService.extractUsername(token);
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username); // will throw an error if no such a user anymore
                if (CustomValidator.userIsAllowedAccess(userDetails)) {
                    User currentUser = ((MyUserDetails) userDetails).getUser();
                    if (jwtService.isValid(token, userDetails)) {
                        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
                                null, userDetails.getAuthorities());
                        authToken.setDetails(
                                new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                        securirtyEventService.doTheSessionAuditIfRequired(currentUser);
                    } else {
                        securirtyEventService.handleSecurityEvent(currentUser, SecurityEventType.TOKEN_INVALID, null);
                    }
                }
                // filterChain.doFilter(request, response);
            }
        } catch (Exception exception) {
            log.error("Error during JWT authentication", exception);
        }
        filterChain.doFilter(request, response);
    }

}
