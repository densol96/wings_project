package lv.wings.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lv.wings.auditing.ApplicationAuditAware;
import lv.wings.filter.JwtAuthFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private final MyUserDetailsMenager userDetailManager;
	private final JwtAuthFilter jwtAuthFilter;

	public SecurityConfig(MyUserDetailsMenager userDetailsMenager, JwtAuthFilter jwtAuthFilter) {
		this.userDetailManager = userDetailsMenager;
		this.jwtAuthFilter = jwtAuthFilter;
	}

	public MyUserDetailsMenager getDetailsService() {
		return new MyUserDetailsMenager();
	}

	/*
	 * @Bean
	 * public DaoAuthenticationProvider createProvider() {
	 * DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
	 * PasswordEncoder encoder =
	 * PasswordEncoderFactories.createDelegatingPasswordEncoder();
	 * 
	 * provider.setPasswordEncoder(encoder);
	 * provider.setUserDetailsService(getDetailsService());
	 * return provider;
	 * }
	 */
	@Bean
	public SecurityFilterChain configurePermissionToEndpoints(HttpSecurity http) throws Exception {
		return http.csrf(csrf -> csrf.disable())
				.cors(Customizer.withDefaults())
				.authorizeHttpRequests(auth -> auth.requestMatchers("/admin/**").hasAuthority("ADMIN")
						.requestMatchers("/admin/api/**").hasAuthority("ADMIN")
						.requestMatchers("/admin/api/events/**").hasAuthority("ADMIN")
						.requestMatchers("/admin/api/events").hasAuthority("ADMIN")
						.requestMatchers("/admin/api/events-categories/**").hasAuthority("ADMIN")
						.requestMatchers("/admin/api/events-categories").hasAuthority("ADMIN")
						.requestMatchers("/admin/api/events-pictures/**").hasAuthority("ADMIN")
						.requestMatchers("/admin/api/events-pictures/create-delete").hasAuthority("ADMIN")

						.requestMatchers("/admin/api/products-categories/**").hasAuthority("ADMIN")
						.requestMatchers("/admin/api/products-pictures/**").hasAuthority("ADMIN")
						.requestMatchers("/admin/api/products/**").hasAuthority("ADMIN")
						.requestMatchers("/admin/api/products").hasAuthority("ADMIN")
						.requestMatchers("/admin/api/products-pictures/create-delete").hasAuthority("ADMIN")
						.anyRequest()
						.permitAll())
				.userDetailsService(userDetailManager)
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
				.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

	@Bean
	public AuditorAware<Integer> auditorAware() {
		return new ApplicationAuditAware();
	}

}
