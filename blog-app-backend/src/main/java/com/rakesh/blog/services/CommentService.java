package com.rakesh.blog.services;

import java.util.List;

import com.rakesh.blog.payloads.request.CommentDto;

public interface CommentService {

    CommentDto createComment(Integer postId, CommentDto commentDto);

    CommentDto updateComment(Integer postId, CommentDto commentDto);

    void deleteComment(Integer commentId);

    List<CommentDto> getAllCommentByPost(Integer postId);

}
