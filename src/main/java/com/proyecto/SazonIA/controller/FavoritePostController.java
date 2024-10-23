package com.proyecto.SazonIA.controller;

import com.proyecto.SazonIA.model.Post;
import com.proyecto.SazonIA.service.FavoritePostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/favorites")
public class FavoritePostController {

    @Autowired
    private FavoritePostService favoritePostService;

    @Operation(summary = "Add a post to favorites")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post added to favorites"),
            @ApiResponse(responseCode = "404", description = "Post not found"),
            @ApiResponse(responseCode = "400", description = "Post already added to favorites")
    })
    @PostMapping
    public ResponseEntity<String> addFavoritePost(@RequestParam Integer userId, @RequestParam String postId) {
        favoritePostService.addFavoritePost(userId, postId);
        return ResponseEntity.ok("Post added to favorites");
    }

    @Operation(summary = "Remove a post from favorites")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post removed from favorites"),
            @ApiResponse(responseCode = "404", description = "Favorite post not found"),
            @ApiResponse(responseCode = "400", description = "Post not found")
    })
    @DeleteMapping("/{userId}/{postId}")
    public ResponseEntity<String> removeFavoritePost(@PathVariable Integer userId, @PathVariable String postId) {
        favoritePostService.removeFavoritePost(userId, postId);
        return ResponseEntity.ok("Post removed from favorites");
    }

    @Operation(summary = "Get content of a favorite post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post content retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Post or favorite not found")
    })
    @GetMapping("/content")
    public ResponseEntity<Post> getFavoritePostContent(@RequestParam Integer userId, @RequestParam String postId) {
        Post post = favoritePostService.getFavoritePostContent(userId, postId);
        return ResponseEntity.ok(post);
    }
}
