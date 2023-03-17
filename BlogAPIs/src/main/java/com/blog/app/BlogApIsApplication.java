package com.blog.app;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class BlogApIsApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(BlogApIsApplication.class, args);
	}
	
	@Bean
	public ModelMapper getModelMapperBean()
	{
		return new ModelMapper();
	}

	@Autowired
	private PasswordEncoder passEncode;
	
	@Override
	public void run(String... args) throws Exception {
		System.out.println(this.passEncode.encode("xyz"));
	}
}
