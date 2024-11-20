package com.proyecto.SazonIA.controller;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.Map;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
//import org.springframework.web.server.ResponseStatusException;

import com.proyecto.SazonIA.model.Post;
import com.proyecto.SazonIA.service.PostService;

@RestController
@RequestMapping("/posts")
@Tag(name = "Posts", description = "Operations related to posts in Sazón.IA")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE,
        RequestMethod.PUT })
public class PostController {

    @Autowired
    private PostService postService;

    private final Gson gson = new Gson();

    @Operation(summary = "Get random posts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Random posts retrieved successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Post.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping("/random")
    public ResponseEntity<String> getRandomPosts(@RequestParam(value = "count", defaultValue = "5") int count) {
        List<Post> randomPosts = postService.getRandomPosts(count);
        // Respuesta compleja con Gson
        Map<String, Object> response = Map.of("status", "success", "data", randomPosts);
        String jsonResponse = gson.toJson(response);
        return ResponseEntity.ok(jsonResponse);
    }

    @Operation(summary = "Get posts by user ID with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Posts retrieved successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Post.class))),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<String> getPostsByUserPaginated(
            @PathVariable Integer userId,
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {

        List<Post> posts = postService.getPostsByUser(userId, page, pageSize);
        // Respuesta compleja con Gson
        if (posts.isEmpty()) {
            Map<String, String> errorResponse = Map.of("status", "error", "message", "No posts found for the user");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(gson.toJson(errorResponse));
        }

        Map<String, Object> response = Map.of("status", "success", "data", posts);
        String jsonResponse = gson.toJson(response);
        return ResponseEntity.ok(jsonResponse);
    }

    @Operation(summary = "Get a post by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Post.class))),
            @ApiResponse(responseCode = "404", description = "Post not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping("/{postId}")
    public ResponseEntity<String> getPostById(@PathVariable String postId) {
        Optional<Post> post = postService.getPostById(postId);
        if (post.isPresent()) {
            // Respuesta compleja con Gson
            Map<String, Object> response = Map.of("status", "success", "data", post.get());
            return ResponseEntity.ok(gson.toJson(response));
        } else {
            // Respuesta de error
            Map<String, String> errorResponse = Map.of("status", "error", "message", "Post not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(gson.toJson(errorResponse));
        }
    }

    @Operation(summary = "Create a new post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Post created successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Post.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PostMapping
    public ResponseEntity<String> createPost(@Valid @RequestBody Post post) {
        Post createdPost = postService.createPost(post.getUserId(), post.getTitle(), post.getContent());
        // Respuesta compleja con Gson
        Map<String, Object> response = Map.of("status", "success", "data", createdPost);
        return ResponseEntity.status(HttpStatus.CREATED).body(gson.toJson(response));
    }

    @Operation(summary = "Update an existing post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post updated successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Post.class))),
            @ApiResponse(responseCode = "404", description = "Post not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PutMapping("/{postId}")
    public ResponseEntity<String> updatePost(
            @PathVariable String postId,
            @RequestParam String title,
            @RequestParam String content) {
        Post updatedPost = postService.updatePost(postId, title, content);
        if (updatedPost != null) {
            Map<String, Object> response = Map.of("status", "success", "data", updatedPost);
            return ResponseEntity.ok(gson.toJson(response));
        } else {
            Map<String, String> errorResponse = Map.of("status", "error", "message", "Post not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(gson.toJson(errorResponse));
        }
    }

    @Operation(summary = "Delete a post by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Post deleted successfully", content = @Content),
            @ApiResponse(responseCode = "404", description = "Post not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @DeleteMapping("/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable String postId) {
        boolean isDeleted = postService.deletePost(postId);
        if (isDeleted) {
            Map<String, String> response = Map.of("status", "success", "message", "Post deleted successfully");
            // Cambiar noContent() por ok() para enviar una respuesta con un cuerpo
            return ResponseEntity.ok(gson.toJson(response));
        } else {
            Map<String, String> errorResponse = Map.of("status", "error", "message", "Post not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(gson.toJson(errorResponse));
        }
    }

}
