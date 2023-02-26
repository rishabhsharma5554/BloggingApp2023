package com.blog.app.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.blog.app.payloads.APIResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<APIResponse> resourceNotFoundException(ResourceNotFoundException e)
	{
		String errorMsg = e.getMessage();
		return new ResponseEntity<>(new APIResponse(errorMsg,false),HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	@ExceptionHandler
	public ResponseEntity<APIResponse> methodArgumentTypeMismatchException(Exception ex)
	{
		String errorMsg = ex.getMessage();
		return new ResponseEntity<>(new APIResponse(errorMsg,false),HttpStatus.INTERNAL_SERVER_ERROR);		
	}
}
