package com.blog.app.exceptions;

public class ResourceNotFoundException extends RuntimeException{
	public String resourceName;
	public String resourceField;
	public Integer fieldValue;
	public ResourceNotFoundException(String resourceName, String resourceField, Integer fieldValue) {
		super(String.format("%s not found with this %s : %s", resourceName,resourceField,fieldValue));
		this.resourceName = resourceName;
		this.resourceField = resourceField;
		this.fieldValue = fieldValue;
	}
	
}
