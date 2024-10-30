package com.proyecto.SazonIA.controller;

import com.proyecto.SazonIA.model.Post;
import com.proyecto.SazonIA.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/posts")
@Tag(name = "Posts", description = "Operations related to posts in Sazón.IA")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE,
        RequestMethod.PUT })
public class PostController {

    @Autowired
    private PostService postService;

    @Operation(summary = "Get 20 random posts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Random posts retrieved successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Post.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping("/random")
    public ResponseEntity<List<Post>> getRandomPosts(@RequestParam(value = "count", defaultValue = "20") int count) {
        List<Post> randomPosts = postService.getRandomPosts(count);
        return ResponseEntity.ok(randomPosts);
    }

    /*
    @Operation(summary = "Get all posts with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Posts retrieved successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Post.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping("/pagination")
    public ResponseEntity<List<Post>> getAllPostsPaginated(
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
        List<Post> posts = postService.getAllPosts(page, pageSize);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }*/

    @Operation(summary = "Get posts by user ID with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Posts retrieved successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Post.class))),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping("/user/{user_id}/posts")
    public ResponseEntity<List<Post>> getPostsByUserPaginated(
            @PathVariable Integer user_id,
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
        List<Post> posts = postService.getPostsByUser(user_id, page, pageSize);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @Operation(summary = "Get a post by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Post.class))),
            @ApiResponse(responseCode = "404", description = "Post not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping("/{postId}")
    public ResponseEntity<Post> getPostById(@PathVariable String postId) {
        Post post = postService.getPostById(postId).orElseThrow();
        return ResponseEntity.ok(post);
    }

    @Operation(summary = "Create a new post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Post created successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Post.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PostMapping
    public ResponseEntity<Post> createPost(@Valid @RequestBody Post post) {
        Post createdPost = postService.createPost(post.getUser_id(), post.getTitle(), post.getContent(),
                post.getMediaUrls());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
    }

    @Operation(summary = "Update an existing post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post updated successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Post.class))),
            @ApiResponse(responseCode = "404", description = "Post not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PutMapping("/{postId}")
    public ResponseEntity<Post> updatePost(@PathVariable String postId, @Valid @RequestBody Post postDetails) {
        Post updatedPost = postService.updatePost(postId, postDetails);
        if (updatedPost != null) {
            return ResponseEntity.ok(updatedPost);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary = "Delete a post by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Post deleted successfully", content = @Content),
            @ApiResponse(responseCode = "404", description = "Post not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable String postId) {
        boolean isDeleted = postService.deletePost(postId);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
