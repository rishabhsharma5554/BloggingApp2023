package com.blog.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.blog.app.config.security.CustomUserDetailsService;

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
