package com.rakesh.blog.services;

import java.util.List;

import com.rakesh.blog.payloads.request.PostDto;
import com.rakesh.blog.payloads.response.PostResponse;

public interface PostService {

	// create
	PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);

	// update
	PostDto updatepost(PostDto postDto, int postId);

	// delete
	void deletePost(int postId);

	// get
	PostDto getPost(int postId);

	// getAll
	PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy);

	// get posts by category
	PostResponse getAllPostsByCategory(int catagoryId, Integer pageNumber, Integer pageSize, String sortBy);

	// get posts by user
	PostResponse getAllPostsByUser(int userId, Integer pageNumber, Integer pageSize, String sortBy);

	// Search post by keyword
	List<PostDto> searchPost(String keyword);
}
