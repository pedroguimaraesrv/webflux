package com.apirest.webflux.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
//import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.crypto.factory.PasswordEncoderFactories;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

import com.apirest.webflux.services.UserServiceImpl;

@EnableWebFluxSecurity
public class SecurityConfig {
	
	@Bean
	public SecurityWebFilterChain securityWebFilterChain( ServerHttpSecurity http) {
		return http
				.csrf().disable()
				.authorizeExchange()
				.pathMatchers(HttpMethod.POST, "/playlists/**").hasRole("ADMIN")//Role_admin
				.pathMatchers(HttpMethod.GET, "/playlists/**").hasRole("USER")//Role_user
				.pathMatchers(HttpMethod.POST, "/users/**").hasRole("ADMIN")//Role_admin
				.pathMatchers(HttpMethod.GET, "/users/**").hasRole("USER")//Role_user
				.anyExchange().authenticated()
				.and()
					.formLogin()
				.and()
					.httpBasic()
				.and()
					.build();
				
	}
	
	@Bean
	ReactiveAuthenticationManager authenticationManager(UserServiceImpl userService) {
		return new UserDetailsRepositoryReactiveAuthenticationManager(userService);
	}
	
//	@Bean
//	public MapReactiveUserDetailsService userDetailsService() {
//		
//		PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
//		UserDetails admin = User.withUsername("admin")
//				.password(passwordEncoder.encode("admin"))
//				.roles("ADMIN")
//				.build();
//		UserDetails user = User.withUsername("pedrohenrique")
//				.password(passwordEncoder.encode("pedrohenrique"))
//				.roles("USER")
//				.build();
//		UserDetails profAdriano = User.withUsername("profadriano")
//				.password(passwordEncoder.encode("adriano"))
//				.roles("USER","ADMIN")
//				.build();
//		
//		return new MapReactiveUserDetailsService(admin, user, profAdriano);
//	}
//	
	
}
