package com.blog.app.services;

import java.util.List;

import com.blog.app.payloads.CategoryDTO;

public interface CategoryService {
	
	//by default all methods are pubilc abstract
	//create
	public CategoryDTO createCategory(CategoryDTO categoryDTO);
	
	//update
	public CategoryDTO updateCategory(CategoryDTO categoryDTO, Integer id);
	
	//delete
	public void deleteCategory(Integer id);
	
	//get
	public CategoryDTO getCategory(Integer id);
	
	//getAll
	List<CategoryDTO> getAllCategory();
	
	
}
