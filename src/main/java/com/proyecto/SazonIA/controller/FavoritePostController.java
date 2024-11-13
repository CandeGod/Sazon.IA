package com.proyecto.SazonIA.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.proyecto.SazonIA.model.FavoritePost;
import com.proyecto.SazonIA.model.FavoritePostId;
import com.proyecto.SazonIA.model.Post;
import com.proyecto.SazonIA.service.FavoritePostService;

import org.springframework.data.domain.Page;

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
        @Operation(summary = "Save a post as favorite")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "Favorite post saved successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = FavoritePost.class))),
                        @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
                        @ApiResponse(responseCode = "404", description = "User or post not found", content = @Content),
                        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
        })
        @PostMapping("/{userId}")
        public ResponseEntity<FavoritePost> saveFavoritePost(@PathVariable Integer userId,
                        @RequestParam String postId) {
                // Crear un nuevo objeto FavoritePostId
                FavoritePostId favoritePostId = new FavoritePostId(userId, postId);

                // Crear un nuevo objeto FavoritePost con el ID generado
                FavoritePost favoritePost = new FavoritePost();
                favoritePost.setId(favoritePostId);

                // Guardar el objeto FavoritePost
                FavoritePost savedFavoritePost = favoritePostService.saveFavoritePost(favoritePost);
                return ResponseEntity.status(HttpStatus.CREATED).body(savedFavoritePost);
        }

        @Operation(summary = "Get all favorite posts by user ID")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Favorite posts retrieved successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = FavoritePost.class))),
                        @ApiResponse(responseCode = "404", description = "User not found", content = @Content),
                        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
        })
        @GetMapping("/{userId}")
        public ResponseEntity<List<FavoritePost>> getFavoritePostsByUserId(@PathVariable Integer userId,
                        @RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "10") int size) {
                // Obtener las publicaciones favoritas con paginación
                Page<FavoritePost> favoritePostsPage = favoritePostService.getFavoritePostsByUserId(userId, page, size);

                // Devolver solo el contenido de la página
                return ResponseEntity.ok(favoritePostsPage.getContent());
        }

        @Operation(summary = "Remove a favorite post")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "204", description = "Favorite post removed successfully", content = @Content),
                        @ApiResponse(responseCode = "404", description = "Favorite post not found", content = @Content),
                        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
        })
        @DeleteMapping("/{userId}")
        public ResponseEntity<Void> removeFavoritePost(@PathVariable Integer userId, @RequestParam String postId) {
                favoritePostService.removeFavoritePost(userId, postId);
                return ResponseEntity.noContent().build();
        }

        @Operation(summary = "Get content of a specific favorite post by user ID and post ID")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Favorite post retrieved successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Post.class))),
                        @ApiResponse(responseCode = "404", description = "Post not found", content = @Content),
                        @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
        })
        @GetMapping("post/{userId}")
        public ResponseEntity<Post> getContentFavoritePostByUserAndPostId(@PathVariable Integer userId,
                        @RequestParam String postId) {
                // Llamar al servicio para obtener la publicación favorita específica del
                // usuario
                Post favoritePost = favoritePostService.getContentFavoritePostByUserIdAndPostId(userId, postId);

                // Devolver el contenido de la publicación como respuesta
                return ResponseEntity.ok(favoritePost);
        }

}
