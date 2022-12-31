package com.rakesh.blog.controllers;

import java.util.List;

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

import com.rakesh.blog.payloads.request.CommentDto;
import com.rakesh.blog.payloads.response.ApiResponse;
import com.rakesh.blog.services.CommentService;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/post/{postId}/addComment")
    public ResponseEntity<?> createComment(@PathVariable Integer postId,
            @RequestBody CommentDto commentDto) {
        CommentDto createdComment = this.commentService.createComment(postId, commentDto);
        return new ResponseEntity<CommentDto>(createdComment, HttpStatus.CREATED);
    }

    @PutMapping("/updateComment/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable Integer commentId,
            @RequestBody CommentDto commentDto) {
        CommentDto updatedComment = this.commentService.updateComment(commentId, commentDto);
        return new ResponseEntity<CommentDto>(updatedComment, HttpStatus.OK);
    }

    @GetMapping("/findComments/post/{postId}")
    public ResponseEntity<?> getAllCommentByPost(@PathVariable Integer postId) {
        List<CommentDto> allCommentByPost = this.commentService.getAllCommentByPost(postId);
        return new ResponseEntity<List<CommentDto>>(allCommentByPost, HttpStatus.OK);
    }

    @DeleteMapping("/deleteComment/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Integer commentId) {
        this.commentService.deleteComment(commentId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Comment is deleted Successfully", true, "200 OK"),
                HttpStatus.OK);
    }

}
