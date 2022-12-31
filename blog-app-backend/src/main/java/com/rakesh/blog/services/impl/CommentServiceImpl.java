package com.rakesh.blog.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rakesh.blog.entities.Comment;
import com.rakesh.blog.entities.Post;
import com.rakesh.blog.exceptions.ResourceNotFoundException;
import com.rakesh.blog.payloads.request.CommentDto;
import com.rakesh.blog.repositories.CommentRepo;
import com.rakesh.blog.repositories.PostRepo;
import com.rakesh.blog.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepo commentRepo;

	@Autowired
	private PostRepo postRepo;

	// @Autowired
	// private UserRepo userRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CommentDto createComment(Integer postId, CommentDto commentDto) {

		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "postId", postId));

		Comment comment = new Comment();
		comment.setContent(commentDto.getContent());
		comment.setPost(post);
		// comment.setUser(user);
		Comment savedComment = this.commentRepo.save(comment);
		return this.modelMapper.map(savedComment, CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		Optional<Comment> comment = this.commentRepo.findById(commentId);
		this.commentRepo.delete(this.modelMapper.map(comment, Comment.class));
	}

	@Override
	public List<CommentDto> getAllCommentByPost(Integer postId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));
		Set<Comment> comments = post.getComments();
		List<CommentDto> allCommentDtos = comments.stream()
				.map(comment -> this.modelMapper.map(comment, CommentDto.class)).collect(Collectors.toList());
		return allCommentDtos;
	}

	@Override
	public CommentDto updateComment(Integer commentId, CommentDto commentDto) {
		Comment comment = this.commentRepo.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "comment id", commentId));
		comment.setContent(commentDto.getContent());
		Comment savedComment = this.commentRepo.save(comment);
		return this.modelMapper.map(savedComment, CommentDto.class);
	}

}
