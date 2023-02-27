package com.blog.app.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

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
	public ResponseEntity<APIResponse> methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex)
	{
		String errorMsg = ex.getMessage();
		return new ResponseEntity<>(new APIResponse(errorMsg,false),HttpStatus.INTERNAL_SERVER_ERROR);		
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String,String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex)
	{
		//extract field
		Map<String,String> resp = new HashMap<>();
		ex.getAllErrors().forEach( error -> {
			String fieldName = ((FieldError)error).getField();
			String message = error.getDefaultMessage();
			resp.put(fieldName, message);
		});
		return new ResponseEntity<>(resp,HttpStatus.BAD_REQUEST);
	}
}