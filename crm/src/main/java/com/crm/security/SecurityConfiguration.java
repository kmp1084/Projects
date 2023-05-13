package com.crm.security;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {
	
	@Bean
	public UserDetailsManager userDetailsManager(DataSource datasource) {
		return new JdbcUserDetailsManager(datasource);
	}
	
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(configurer -> configurer
				.requestMatchers("/").hasAnyRole("GUEST", "MANAGER", "ADMIN")
				.requestMatchers("/lead/addLead/**").hasAnyRole("GUEST", "MANAGER", "ADMIN")
				.requestMatchers("/lead/update/**").hasAnyRole("MANAGER", "ADMIN")
				.requestMatchers("/lead/delete/**").hasRole("ADMIN")
				.anyRequest().authenticated())
		.formLogin(form -> form.loginPage("/sign_in").loginProcessingUrl("/authenticateTheUser").permitAll())
		.logout(logout -> logout.permitAll())
		.exceptionHandling(configurer -> configurer.accessDeniedPage("/access-denied"));
		return http.build();
	}
	
	/*
	@Bean
	public InMemoryUserDetailsManager userDetailsManager() {
		UserDetails guest = User.builder().username("guest").password("{noop}guest").roles("GUEST").build();
		UserDetails manager = User.builder().username("manager").password("{noop}manager").roles("MANAGER").build();
		UserDetails admin = User.builder().username("admin").password("{noop}admin").roles("ADMIN").build();
		return new InMemoryUserDetailsManager(guest, manager, admin);
	}
	*/
}
