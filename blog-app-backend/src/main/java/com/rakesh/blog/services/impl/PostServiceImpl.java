package com.rakesh.blog.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.rakesh.blog.entities.Category;
import com.rakesh.blog.entities.Post;
import com.rakesh.blog.entities.User;
import com.rakesh.blog.exceptions.ResourceNotFoundException;
import com.rakesh.blog.payloads.request.CategoryDto;
import com.rakesh.blog.payloads.request.CommentDto;
import com.rakesh.blog.payloads.request.PostDto;
import com.rakesh.blog.payloads.request.UserDto;
import com.rakesh.blog.payloads.response.PostResponse;
import com.rakesh.blog.repositories.CategoryRepo;
import com.rakesh.blog.repositories.PostRepo;
import com.rakesh.blog.repositories.UserRepo;
import com.rakesh.blog.services.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepo postRepo;

	@Autowired
	private CategoryRepo categoryRepo;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {

		// find user and category
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "user id", userId));
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "category id", categoryId));

		// post
		Post post = this.modelMapper.map(postDto, Post.class);
		post.setImage("default.png");
		post.setDate(new Date());
		post.setUser(user);
		post.setCategory(category);
		Post createdPost = this.postRepo.save(post);
		return this.modelMapper.map(createdPost, PostDto.class);
	}

	@Override
	public PostDto updatepost(PostDto postDto, int postId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImage(postDto.getImage());
		Post updatedPost = this.postRepo.save(post);
		return this.modelMapper.map(updatedPost, PostDto.class);
	}

	@Override
	public void deletePost(int postId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));
		this.postRepo.delete(post);

	}

	@Override
	public PostDto getPost(int postId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));
		return this.modelMapper.map(post, PostDto.class);
	}

	@Override
	public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy) {
		Pageable p = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
		Page<Post> postPage = this.postRepo.findAll(p);
		List<Post> posts = postPage.getContent();
		List<PostDto> postDtos = posts.stream().map(post -> this.postToPostDto(post))
				.collect(Collectors.toList());
		return createPostResponse(postPage, postDtos);

	}

	@Override
	public PostResponse getAllPostsByCategory(int catagoryId, Integer pageNumber, Integer pageSize, String sortBy) {
		Category category = this.categoryRepo.findById(catagoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "category Id", catagoryId));
		Pageable p = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
		Page<Post> pagesFindByCategory = this.postRepo.findByCategory(category, p);
		List<Post> postList = pagesFindByCategory.getContent();
		List<PostDto> postDtos = postList.stream().map(post -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		return createPostResponse(pagesFindByCategory, postDtos);
	}

	@Override
	public PostResponse getAllPostsByUser(int userId, Integer pageNumber, Integer pageSize, String sortBy) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "user Id", userId));
		Pageable p = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy));
		Page<Post> postPages = this.postRepo.findByUser(user, p);
		List<Post> posts = postPages.getContent();
		List<PostDto> postDtos = posts.stream().map(post -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		return createPostResponse(postPages, postDtos);
	}

	@Override
	public List<PostDto> searchPost(String keyword) {
		List<Post> searchedPosts = this.postRepo.findByTitleContaining(keyword);
		List<PostDto> posts = searchedPosts.stream().map(post -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		return posts;
	}

	private PostResponse createPostResponse(Page<?> postPage, List<PostDto> postDtos) {
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(postPage.getNumber());
		postResponse.setPageSize(postPage.getSize());
		postResponse.setTotalElements(postPage.getTotalElements());
		postResponse.setTotalPages(postPage.getTotalPages());
		postResponse.setFirstPage(postPage.isFirst());
		postResponse.setLastPage(postPage.isLast());
		return postResponse;
	}

	private PostDto postToPostDto(Post post) {
		PostDto postDto = new PostDto();
		postDto.setId(post.getId());
		postDto.setTitle(post.getTitle());
		postDto.setContent(post.getContent());
		postDto.setImage(post.getImage());
		postDto.setDate(post.getDate());
		postDto.setCategory(this.modelMapper.map(post.getCategory(), CategoryDto.class));
		postDto.setUser(this.modelMapper.map(post.getUser(), UserDto.class));
		postDto.setComments(post.getComments().stream().map(comment -> this.modelMapper.map(comment, CommentDto.class))
				.collect(Collectors.toSet()));
		return postDto;
	}

}
