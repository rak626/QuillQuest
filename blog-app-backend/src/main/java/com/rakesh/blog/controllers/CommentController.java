package com.rakesh.blog.controllers;

import com.rakesh.blog.payloads.request.CommentDto;
import com.rakesh.blog.payloads.response.ApiResponse;
import com.rakesh.blog.services.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@AllArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/post/{postId}/addComment")
    public ResponseEntity<?> createComment(@PathVariable Integer postId,
            @RequestBody CommentDto commentDto) {
        CommentDto createdComment = this.commentService.createComment(postId, commentDto);
        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }

    @PutMapping("/updateComment/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable Integer commentId,
            @RequestBody CommentDto commentDto) {
        CommentDto updatedComment = this.commentService.updateComment(commentId, commentDto);
        return new ResponseEntity<>(updatedComment, HttpStatus.OK);
    }

    @GetMapping("/findComments/post/{postId}")
    public ResponseEntity<?> getAllCommentByPost(@PathVariable Integer postId) {
        List<CommentDto> allCommentByPost = this.commentService.getAllCommentByPost(postId);
        return new ResponseEntity<>(allCommentByPost, HttpStatus.OK);
    }

    @DeleteMapping("/deleteComment/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Integer commentId) {
        this.commentService.deleteComment(commentId);
        return new ResponseEntity<>(new ApiResponse("Comment is deleted Successfully", true, "200 OK"),
                HttpStatus.OK);
    }

}
