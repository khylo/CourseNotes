package com.khylo.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * Builder pattern. We configure the Authentication Maanger by updating the ConfigurerAdapter
 * We tell it things like
 * What kind of Auth (in memory/ DB / ldap)
 * What perms
 * What users
 * 		By default these can be set by 
 * 			spring.security.user.name
 * 			spring.security.user.password 
 * 
 * We set this using configure class.
 */
@EnableWebSecurity
public class SecurityConfig  extends WebSecurityConfigurerAdapter{

	/** Taken from https://spring.io/guides/gs/securing-web/
	 * See also https://www.baeldung.com/spring-security-openid-connect
	 */
	// @Bean
	// @Override
	// public UserDetailsService userDetailsService() {
	// 	UserDetails user =
	// 		 User.withDefaultPasswordEncoder()
	// 			.username("user")
	// 			.password("password")
	// 			.roles("USER")
	// 			.build();

	// 	return new InMemoryUserDetailsManager(user);
	// }

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// First set your type of authentication
		auth.inMemoryAuthentication()
			.withUser("user")
			.password("$2a$10$.u38SayM4yxY6x7rk9adw.INQNFjNd8VyHEbHNLIOwPaV0zAggBa.")
			.roles("USER")
		.and()
			.withUser("admin")
			.password("$2a$10$RYcq01fag6CaU2c0qiLuROGb.CIhLkTP8D5ICiDU4haaGVN1t8Xbm")
			.roles("ADMIN")
		.and()
			.withUser("root")
			.password("$2a$10$eYZ8JfMSFV3Or4fLkHO7NueCVPFGTHjxx2c3jkCTdaolNRABtFzam")
			.roles("ADMIN");	
	}
	

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/user").hasAnyRole("USER", "ADMIN")
			.antMatchers("/admin").hasAnyRole("ADMIN")
			.antMatchers("/hello*").permitAll()
			.and().formLogin();
	}



	/* Sample In memory configure*/
	// protected void inMemory(AuthenticationManagerBuilder auth) throws Exception {
	// 	// First set your type of authentication
	// 	auth.inMemoryAuthentication()
	// 		.withUser("user")
	// 		.password("password")
	// 		.roles("USER");
	// }

	// @Bean
	// public PasswordEncoder getNullPwdEncoder(){
	// 	return NoOpPasswordEncoder.getInstance();		 
	// }

	@Bean
	public PasswordEncoder getPwdEncoder(){
		return new BCryptPasswordEncoder();
	}
	
}
