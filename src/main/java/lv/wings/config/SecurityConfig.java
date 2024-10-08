package lv.wings.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Bean
	public UserDetailsManager createDemoUser() {
		
		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		
		UserDetails details1 = User.builder()
				.username("annija.user")
				.password(encoder.encode("123"))
				.authorities("USER")
				.build();
		
		UserDetails details2 = User.builder()
				.username("annija.admin")
				.password(encoder.encode("456"))
				.authorities("ADMIN")
				.build();
		
		UserDetails details3 = User.builder()
				.username("user.user")
				.password(encoder.encode("789"))
				.authorities("USER")
				.build();
		
		return new InMemoryUserDetailsManager(details1, details2, details3);
		
	}

	@Bean
	public SecurityFilterChain configurePermissionToEndpoints(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(auth->auth
				.requestMatchers("/par-biedribu").permitAll()
				.requestMatchers("/atlaide/**").hasAuthority("ADMIN")
				.requestMatchers("/kontakti").permitAll()
				.requestMatchers("/jaunumi").permitAll()
				.requestMatchers("/jaunumi/{id}").permitAll()
				.requestMatchers("/jaunumi/delete/{id}").hasAuthority("ADMIN")
				.requestMatchers("/jaunumi/sort/{sortType}").permitAll()
				.requestMatchers("/jaunumi/add").hasAuthority("ADMIN")
				.requestMatchers("/jaunumi/update/{id}").hasAuthority("ADMIN")
				.requestMatchers("/").permitAll()
				.requestMatchers("/kategorijas/show/all/**").permitAll()
				.requestMatchers("/kategorijas/remove/{id}").hasAuthority("ADMIN")
				.requestMatchers("/kategorijas/add").hasAuthority("ADMIN")
				.requestMatchers("/kategorijas/update/{id}").hasAuthority("ADMIN")
				.requestMatchers("/error").permitAll()
				.requestMatchers("/pasakuma-bilde").permitAll() //TODO: izrunāt ko ar šo endpointu darām
				.requestMatchers("/pasakuma-kategorija/show/all/**").permitAll()
				.requestMatchers("/pasakuma-kategorija/remove/{id}").hasAuthority("ADMIN")
				.requestMatchers("/pasakuma-kategorija/add").hasAuthority("ADMIN")
				.requestMatchers("/pasakuma-kategorija/update/{id}").hasAuthority("ADMIN")
				.requestMatchers("/pasakuma-komentars/show/all/**").permitAll()
				.requestMatchers("/pasakuma-komentars/remove/{id}").hasAuthority("ADMIN")
				.requestMatchers("/pasakuma-komentars/add/{pasakumsId}").hasAuthority("ADMIN")
				.requestMatchers("/pasakuma-komentars/update/{id}").hasAuthority("ADMIN")
				.requestMatchers("/piegades/veids/show/all/**").permitAll()
				.requestMatchers("/piegades/veids/remove/{id}").hasAuthority("ADMIN")
				.requestMatchers("/piegades/veids/add").hasAuthority("ADMIN")
				.requestMatchers("/piegades/update/{id}").hasAuthority("ADMIN")
				.requestMatchers("/pircejs/**").hasAuthority("ADMIN")
				.requestMatchers("/pirkuma/elements/**").hasAuthority("ADMIN") //TODO iespējams šeit vajag access uz pirkuma elementu by id picējam
				.requestMatchers("/pirkums/show/all").permitAll() //TODO pārrunat šo endpontu
				.requestMatchers("/pirkums/show/all/{id}").permitAll() //TODO pārrunat šo endpontu
				.requestMatchers("/pirkums/remove/{id}").hasAuthority("ADMIN")
				.requestMatchers("/pirkums/add").hasAuthority("ADMIN")
				.requestMatchers("/prece/show/all/**").permitAll()
				.requestMatchers("/prece/remove/{id}").hasAuthority("ADMIN")
				.requestMatchers("/prece/add/{kategorijasid}").hasAuthority("ADMIN")
				.requestMatchers("/prece/update/{id}").hasAuthority("ADMIN")
				.requestMatchers("/preces/bilde/show/all/**").permitAll()
				.requestMatchers("/preces/bilde/remove/{id}").hasAuthority("ADMIN")
				.requestMatchers("/preces/bilde/add/{precesid}").hasAuthority("ADMIN")
				.requestMatchers("/preces/bilde/update/{id}").hasAuthority("ADMIN")
				.requestMatchers("/samaksas/veids/show/all/**").permitAll()
				.requestMatchers("/samaksas/veids/remove/{id}").hasAuthority("ADMIN")
				.requestMatchers("/samaksas/veids/add").hasAuthority("ADMIN")
				.requestMatchers("/samaksas/veids/update/{id}").hasAuthority("ADMIN")
				);
		
		http.formLogin(auth->auth.permitAll());
		
		return http.build();
	}

}
