package com.rakesh.blog.payloads.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {

	private int categoryId;

	@NotBlank(message = "Category Title must not be blank")
	@Size(min = 4, message = "Category Title size must be atleast 4")
	private String categoryTitle;

	@NotBlank(message = "Category Description must not be blank")
	@Size(min = 10, message = "Category Description size must be atleast 10")
	private String categoryDescription;

}
