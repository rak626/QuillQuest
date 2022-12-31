package com.rakesh.blog.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rakesh.blog.config.AppConstants;
import com.rakesh.blog.payloads.request.PostDto;
import com.rakesh.blog.payloads.response.ApiResponse;
import com.rakesh.blog.payloads.response.PostResponse;
import com.rakesh.blog.services.FileService;
import com.rakesh.blog.services.PostService;

@RestController
@RequestMapping("/api")
public class PostController {

	@Autowired
	private PostService postService;

	@Autowired
	private FileService fileService;

	@Value("${project.image}")
	private String path;

	// create
	@PostMapping("/users/{userId}/categories/{categoryId}/posts")
	public ResponseEntity<?> createPost(@RequestBody PostDto postDto, @PathVariable Integer userId,
			@PathVariable Integer categoryId) {
		PostDto createdPost = this.postService.createPost(postDto, userId, categoryId);
		return new ResponseEntity<PostDto>(createdPost, HttpStatus.CREATED);
	}

	// update
	@PutMapping("/posts/{postId}")
	public ResponseEntity<?> updatePost(@RequestBody PostDto postDto, @PathVariable Integer postId) {
		PostDto updatedPostDto = this.postService.updatepost(postDto, postId);
		return new ResponseEntity<PostDto>(updatedPostDto, HttpStatus.OK);
	}

	// delete
	@DeleteMapping("/posts/{postId}")
	public ResponseEntity<?> deletePost(@PathVariable Integer postId) {
		this.postService.deletePost(postId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Post is deleted Successfully", true, "200 OK"),
				HttpStatus.OK);
	}

	// get post by id
	@GetMapping("/posts/{postId}")
	public ResponseEntity<?> getPostById(@PathVariable Integer postId) {
		PostDto postDto = this.postService.getPost(postId);
		return ResponseEntity.ok(postDto);
	}

	// get all post
	@GetMapping("/posts")
	public ResponseEntity<?> getAllPosts(
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy) {
		PostResponse postResponse = this.postService.getAllPost(pageNumber, pageSize, sortBy);
		return ResponseEntity.ok(postResponse);
	}

	// get all post by category
	@GetMapping("/categories/{categoryId}/posts")
	public ResponseEntity<?> getAllPostsByCategory(@PathVariable Integer categoryId,
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy) {
		PostResponse allPostsByCategory = this.postService.getAllPostsByCategory(categoryId, pageNumber, pageSize,
				sortBy);
		return ResponseEntity.ok(allPostsByCategory);
	}

	// get all post by category
	@GetMapping("/users/{userId}/posts")
	public ResponseEntity<?> getAllPostsByUser(@PathVariable Integer userId,
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy) {
		PostResponse allPostsByCategory = this.postService.getAllPostsByUser(userId, pageNumber, pageSize, sortBy);
		return ResponseEntity.ok(allPostsByCategory);
	}

	// search by keyword
	@GetMapping("/posts/search/{keyword}")
	public ResponseEntity<List<PostDto>> searchPost(@PathVariable String keyword) {
		List<PostDto> searchPost = this.postService.searchPost(keyword);
		return ResponseEntity.ok(searchPost);
	}

	// Post image upload
	@PostMapping("/posts/image/upload/{postId}")
	public ResponseEntity<?> uploadPostImage(@RequestParam("image") MultipartFile image, @PathVariable Integer postId)
			throws IOException {
		PostDto post = this.postService.getPost(postId);
		String fileName = this.fileService.uploadImage(path, image);
		post.setImage(fileName);
		PostDto updatedpost = this.postService.updatepost(post, postId);
		return new ResponseEntity<PostDto>(updatedpost, HttpStatus.OK);
	}

	@GetMapping(value = "/posts/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
	public void downlodaImage(@PathVariable String imageName, HttpServletResponse response) throws IOException {
		InputStream resource = this.fileService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
	}

}