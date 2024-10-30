package com.proyecto.SazonIA.controller;

import com.proyecto.SazonIA.model.FavoritePost;
import com.proyecto.SazonIA.model.Post;
import com.proyecto.SazonIA.service.FavoritePostService;
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

@RestController
@RequestMapping("/favoritePosts")
@Tag(name = "Favorite Posts", description = "Operations related to favorite posts in Sazón.IA")
public class FavoritePostController {

    @Autowired
    private FavoritePostService favoritePostService;

    @Operation(summary = "Get content of favorite posts by user ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Favorite posts retrieved successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Post.class))),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping("/posts/{user_id}")
    public ResponseEntity<List<Post>> getContentFavoritePostsByUser(@PathVariable Integer user_id) {
        // Llamar al servicio para obtener las publicaciones favoritas del usuario
        List<Post> favoritePosts = favoritePostService.getContentFavoritePostsByUserId(user_id);
        
        // List<Post> favoritePosts = favoritePostService.getContentFavoritePostsByUserId(userId);
        
        // Devolver la lista de publicaciones como respuesta
        return ResponseEntity.ok(favoritePosts);
    }

    @Operation(summary = "Save a post as favorite")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Favorite post saved successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = FavoritePost.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "User or post not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PostMapping("/{user_id}/{postId}")
    public ResponseEntity<FavoritePost> saveFavoritePost(@PathVariable Integer user_id, @PathVariable String postId) {
        FavoritePost savedFavoritePost = favoritePostService.saveFavoritePost(user_id, postId);
        return ResponseEntity.ok(savedFavoritePost);
    }

    @Operation(summary = "Get all favorite posts by user ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Favorite posts retrieved successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = FavoritePost.class))),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping("/{user_id}")
    public ResponseEntity<List<FavoritePost>> getFavoritePostsByUser_id(@PathVariable Integer user_id) {
        List<FavoritePost> favoritePosts = favoritePostService.getFavoritePostsByUserId(user_id);
        return ResponseEntity.ok(favoritePosts);
    }

    @Operation(summary = "Remove a favorite post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Favorite post removed successfully", content = @Content),
            @ApiResponse(responseCode = "404", description = "Favorite post not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @DeleteMapping("/{user_id}/{postId}")
    public ResponseEntity<Void> removeFavoritePost(@PathVariable Integer user_id, @PathVariable String postId) {
        favoritePostService.removeFavoritePost(user_id, postId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Check if a post is favorited by user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Post favoritism status retrieved successfully", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "User or post not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping("/check/{user_id}/{postId}")
    public ResponseEntity<Boolean> isPostFavoritedByUser(@PathVariable Integer user_id, @PathVariable String postId) {
        boolean isFavorited = favoritePostService.isPostFavoritedByUser(user_id, postId);
        return ResponseEntity.ok(isFavorited);
    }
}
