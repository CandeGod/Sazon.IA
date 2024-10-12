package com.proyecto.SazonIA.controller;

import com.proyecto.SazonIA.model.CommentPost;
import com.proyecto.SazonIA.service.CommentPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/comment")
@Tag(name = "Comments", description = "Operations related to comments on posts in Sazón.IA")
public class CommentPostController {

    @Autowired
    private CommentPostService commentService;

    @Operation(summary = "Add a new comment")
    @PostMapping
    public CommentPost addComment(@RequestBody CommentPost comment) {
        // Puedes omitir el commentId y commentDate del objeto, ya que se generan automáticamente
        return commentService.addComment(comment);
    }

    @Operation(summary = "Get all comments for a specific post with pagination")
    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentPost>> getCommentsByPostId(
            @PathVariable String postId,
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
        List<CommentPost> comments = commentService.getCommentsByPostId(postId, page, pageSize);
        return ResponseEntity.ok(comments);
    }

    @Operation(summary = "Delete a comment by ID")
    @DeleteMapping("/{commentId}")
    public void deleteComment(@PathVariable String commentId) {
        commentService.deleteComment(commentId);
    }
}
