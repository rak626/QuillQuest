package com.rakesh.blog.controllers;

import com.rakesh.blog.payloads.request.CategoryDto;
import com.rakesh.blog.payloads.response.ApiResponse;
import com.rakesh.blog.services.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
@AllArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

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
