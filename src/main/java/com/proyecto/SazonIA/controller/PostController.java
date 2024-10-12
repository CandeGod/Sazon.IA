package com.proyecto.SazonIA.controller;

import com.proyecto.SazonIA.model.Post;
import com.proyecto.SazonIA.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/posts")
@Tag(name = "Posts", description = "Operations related to posts in Sazón.IA")
public class PostController {

    @Autowired
    private PostService postService;

    @Operation(summary = "Get all posts")
    @GetMapping
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }

    @Operation(summary = "Get all posts with pagination")
    @GetMapping("/pagination")
    public List<Post> getAllPostsPaginated(
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
        return postService.getAllPosts(page, pageSize);
    }

    @Operation(summary = "Get a post by ID")
    @GetMapping("/{postId}")
    public ResponseEntity<Post> getPostById(@PathVariable String postId) {
        Optional<Post> post = postService.getPostById(postId);
        return post.map(ResponseEntity::ok)
                   .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found"));
    }

    @Operation(summary = "Create a new post")
    @PostMapping
    public ResponseEntity<Post> createPost(@Valid @RequestBody Post post) {
        Post createdPost = postService.createPost(post);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
    }

    @Operation(summary = "Update an existing post")
    @PutMapping("/{postId}")
    public ResponseEntity<Post> updatePost(@PathVariable String postId, @Valid @RequestBody Post postDetails) {
        Post updatedPost = postService.updatePost(postId, postDetails);
        if (updatedPost != null) {
            return ResponseEntity.ok(updatedPost);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Delete a post by ID")
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable String postId) {
        boolean isDeleted = postService.deletePost(postId);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}