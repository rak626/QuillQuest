package com.rakesh.blog.payloads.request;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PostDto {

	private Integer id;
	private String title;
	private String content;
	private String image;
	private Date date;
	private UserDto user;
	private CategoryDto category;
	private Set<CommentDto> comments = new HashSet<>();

}
