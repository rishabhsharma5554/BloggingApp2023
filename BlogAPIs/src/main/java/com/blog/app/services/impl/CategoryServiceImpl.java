package com.blog.app.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.app.entites.Category;
import com.blog.app.exceptions.ResourceNotFoundException;
import com.blog.app.payloads.CategoryDTO;
import com.blog.app.repos.CategoryRepo;
import com.blog.app.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepo catRepo;
	
	@Autowired 
	private ModelMapper modelMapper;
	
	@Override
	public CategoryDTO createCategory(CategoryDTO categoryDTO) {
		Category category = this.dtoToEntity(categoryDTO);
	 	Category addedCategory = this.catRepo.save(category);
		return this.entityToDTO(addedCategory);
	}

	@Override
	public CategoryDTO updateCategory(CategoryDTO categoryDTO, Integer id) {
		Category category = this.catRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category", " category id ", id));
		category.setCategoryTitle(categoryDTO.getCategoryTitle());
		category.setCategoryDesc(categoryDTO.getCategoryDesc());
		Category savedCategory = this.catRepo.save(category);
		return this.entityToDTO(savedCategory);
	}

	@Override
	public void deleteCategory(Integer id) {
		this.catRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category", "category id ", id));
		this.catRepo.deleteById(id);
	}

	@Override
	public CategoryDTO getCategory(Integer id) {
		Category category = this.catRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category", " category id ", id));
		return this.entityToDTO(category);
	}

	@Override
	public List<CategoryDTO> getAllCategory() {
		List<Category> allCategory = this.catRepo.findAll();
		List<CategoryDTO> allCategoryDTO =  allCategory.stream().map(cat -> this.entityToDTO(cat)).collect(Collectors.toList());
		return allCategoryDTO;
	}
	
	private CategoryDTO entityToDTO(Category category)
	{
		return this.modelMapper.map(category, CategoryDTO.class);
	}
	
	private Category dtoToEntity(CategoryDTO categoryDTO)
	{
		return this.modelMapper.map(categoryDTO, Category.class);
	}

}
