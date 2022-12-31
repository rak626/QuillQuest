package com.rakesh.blog.controllers;

import java.util.List;

import javax.validation.Valid;

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

import com.rakesh.blog.payloads.request.CategoryDto;
import com.rakesh.blog.payloads.response.ApiResponse;
import com.rakesh.blog.services.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	// Post
	@PostMapping("/")
	public ResponseEntity<?> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
		CategoryDto createdCategory = this.categoryService.createCategory(categoryDto);
		return new ResponseEntity<CategoryDto>(createdCategory, HttpStatus.CREATED);
	}

	// Put
	@PutMapping("/{categoryId}")
	public ResponseEntity<?> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable int categoryId) {
		CategoryDto updatedCategory = this.categoryService.updateCategory(categoryDto, categoryId);
		return ResponseEntity.ok(updatedCategory);

	}

	// Delete
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<?> deleteCategory(@PathVariable int categoryId) {
		this.categoryService.deleteCategory(categoryId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Category Deleted Successfully", true, "200 OK"),
				HttpStatus.OK);
	}

	// Get
	@GetMapping("/{categoryId}")
	public ResponseEntity<?> getCategory(@PathVariable int categoryId) {
		CategoryDto category = this.categoryService.getCategory(categoryId);
		return ResponseEntity.ok(category);
	}

	// Get All
	@GetMapping("/")
	public ResponseEntity<?> getAllCategory() {
		List<CategoryDto> allCategoriesList = this.categoryService.getAllCategory();
		return ResponseEntity.ok(allCategoriesList);
	}

}
