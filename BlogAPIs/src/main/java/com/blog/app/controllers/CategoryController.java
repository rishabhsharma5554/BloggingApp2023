package com.blog.app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.app.payloads.APIResponse;
import com.blog.app.payloads.CategoryDTO;
import com.blog.app.services.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

	@Autowired
	private CategoryService catService;
	
	//create
	@PostMapping("/")
	public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO catDto)
	{
		CategoryDTO createCategory = this.catService.createCategory(catDto);
		return new ResponseEntity<CategoryDTO>(createCategory,HttpStatus.CREATED);
	}
	
	//update
	@PutMapping("/{id}")
	public ResponseEntity<CategoryDTO> upodateCategory(@Valid @RequestBody CategoryDTO catDTO, @PathVariable("id") Integer id)
	{
		CategoryDTO updateCategory = this.catService.updateCategory(catDTO, id);
		return new ResponseEntity<CategoryDTO>(updateCategory,HttpStatus.OK);
	}
	
	//delete
	@DeleteMapping("/{id}")
	public ResponseEntity<APIResponse> deleteCategory(@PathVariable("id") Integer id)
	{
		this.catService.deleteCategory(id);
		return new ResponseEntity<APIResponse>(new APIResponse("Deleted",true),HttpStatus.OK);
	}
	
	
	//get by id
	@GetMapping("/{id}")
	public ResponseEntity<CategoryDTO> getCategory(@PathVariable Integer id)
	{
		CategoryDTO category = this.catService.getCategory(id);
		return new ResponseEntity<CategoryDTO>(category,HttpStatus.OK);
	}
	
	
	//getAll
	@GetMapping("/")
	public ResponseEntity<List<CategoryDTO>> getAllCategory()
	{
		List<CategoryDTO> allCategory = this.catService.getAllCategory();
		return new ResponseEntity<List<CategoryDTO>>(allCategory,HttpStatus.OK);
	}
	
}
