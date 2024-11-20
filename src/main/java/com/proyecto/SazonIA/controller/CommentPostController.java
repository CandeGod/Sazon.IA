package com.proyecto.SazonIA.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.proyecto.SazonIA.model.CommentPost;
import com.proyecto.SazonIA.service.CommentPostService;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/commentsPost")
@Tag(name = "Comments on Posts", description = "Operations related to comments on posts in Sazón.IA")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE,
    RequestMethod.PUT })
public class CommentPostController {

    @Autowired
    private CommentPostService commentService;

    @Operation(summary = "Add a new comment to a specific post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Comment added successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CommentPost.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Post not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PostMapping("/{postId}")
    public ResponseEntity<CommentPost> addComment(@PathVariable String postId, @RequestBody CommentPost comment) {
        comment.setPostId(postId); // Establecer el postId desde la URL
        CommentPost createdComment = commentService.addComment(comment);
        return ResponseEntity.status(201).body(createdComment);
    }

    @Operation(summary = "Get all comments for a specific post with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comments retrieved successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CommentPost.class))),
            @ApiResponse(responseCode = "404", description = "Post not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping("/{postId}")
    public ResponseEntity<List<CommentPost>> getCommentsByPostId(
            @PathVariable String postId,
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
        List<CommentPost> comments = commentService.getCommentsByPostId(postId, page, pageSize);
        return ResponseEntity.ok(comments);
    }

    @Operation(summary = "Edit a comment by ID and Post ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comment updated successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CommentPost.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Comment or Post not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PutMapping("/{postId}")
    public ResponseEntity<CommentPost> updateComment(
            @PathVariable String postId,
            @RequestParam String commentId,
            @RequestParam String content) {

        CommentPost editedComment = commentService.editComment(postId, commentId, content);
        return ResponseEntity.ok(editedComment);
    }

    @Operation(summary = "Delete a comment by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Comment deleted successfully", content = @Content),
            @ApiResponse(responseCode = "404", description = "Comment not found or user not authorized", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable String postId,
            @RequestParam String commentId) {

        boolean isDeleted = commentService.deleteComment(postId, commentId);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(404).build();
        }
    }

}
