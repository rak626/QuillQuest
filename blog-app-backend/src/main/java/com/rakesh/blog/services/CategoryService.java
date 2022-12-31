package com.rakesh.blog.services;

import java.util.List;

import com.rakesh.blog.payloads.request.CategoryDto;

public interface CategoryService {

	// create
	CategoryDto createCategory(CategoryDto categoryDto);

	// update
	CategoryDto updateCategory(CategoryDto categoryDto, int categoryId);

	// Delete
	void deleteCategory(int categoryId);

	// get
	CategoryDto getCategory(int categoryId);

	// getAll
	List<CategoryDto> getAllCategory();

}
