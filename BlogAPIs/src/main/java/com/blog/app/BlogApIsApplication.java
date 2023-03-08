package com.blog.app;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.blog.app.payloads.PostResponse;

@SpringBootApplication
public class BlogApIsApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogApIsApplication.class, args);
	}
	
	@Bean
	public ModelMapper getModelMapperBean()
	{
		return new ModelMapper();
	}
	
//	@Bean
//	public PostResponse getPostResponse()
//	{
//		return new PostResponse();
//	}
}
