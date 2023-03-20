package com.blog.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.blog.app.config.security.CustomUserDetailsService;
import com.blog.app.config.security.JWTAuthEntryPoint;
import com.blog.app.config.security.JwtAuthenticationFilter;
/*
@Configuration
@EnableWebSecurity
public class SpringBasicAuthForAPI {
	
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	
	//basic auth
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf()
			.disable()
			.authorizeHttpRequests()
			.anyRequest()
			.authenticated()
			.and()
			.httpBasic();
		return http.build();
	}
	
	//basic api auth
	 @Bean
	 public AuthenticationManager authManager(HttpSecurity http) 
	   throws Exception {
	     return http.getSharedObject(AuthenticationManagerBuilder.class)
	       .userDetailsService(this.customUserDetailsService)
	       .passwordEncoder(bCryptPasswordEncoder())
	       .and()
	       .build();
	 }
	
	@Bean
	public PasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}

*/
@Configuration
@EnableWebSecurity
public class SpringBasicAuthForAPI {
	
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Autowired
	private JWTAuthEntryPoint jwyAuthEntryPoint;
	
	@Autowired
	private JwtAuthenticationFilter jwtAuthFilter;
	//basic auth
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf()
			.disable()
			.authorizeHttpRequests()
			.requestMatchers("/api/v1/auth/login").permitAll()
			.anyRequest()
			.authenticated()
			.and()
			.exceptionHandling()
			.authenticationEntryPoint(this.jwyAuthEntryPoint)
			.and()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS);	
		
		http.addFilterBefore(this.jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
	    return http.build();
	}
	
	//basic api auth
	 @Bean
	 public AuthenticationManager authManager(HttpSecurity http) 
	   throws Exception {
	     return http.getSharedObject(AuthenticationManagerBuilder.class)
	       .userDetailsService(this.customUserDetailsService)
	       .passwordEncoder(bCryptPasswordEncoder())
	       .and()
	       .build();
	 }
	
	@Bean
	public PasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
