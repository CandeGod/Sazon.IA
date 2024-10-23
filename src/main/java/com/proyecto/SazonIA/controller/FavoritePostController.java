package com.proyecto.SazonIA.controller;

import com.proyecto.SazonIA.model.FavoritePost;
import com.proyecto.SazonIA.model.Post;
import com.proyecto.SazonIA.service.FavoritePostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/favoritePosts")
public class FavoritePostController {

    @Autowired
    private FavoritePostService favoritePostService;

    // Método para obtener el contenido de las publicaciones guardadas como favoritas por un usuario
    @GetMapping("/posts/{userId}")
    public ResponseEntity<List<Post>> getContentFavoritePostsByUser(@PathVariable Integer userId) {
        // Llamar al servicio para obtener las publicaciones favoritas del usuario
        List<Post> favoritePosts = favoritePostService.getContentFavoritePostsByUserId(userId);
        
        // Devolver la lista de publicaciones como respuesta
        return ResponseEntity.ok(favoritePosts);
    }

    // Guardar una publicación como favorita
    @PostMapping("/{userId}/{postId}")
    public ResponseEntity<FavoritePost> saveFavoritePost(@PathVariable Integer userId, @PathVariable String postId) {
        FavoritePost savedFavoritePost = favoritePostService.saveFavoritePost(userId, postId);
        return ResponseEntity.ok(savedFavoritePost);
    }

    // Obtener todas las publicaciones favoritas de un usuario
    @GetMapping("/{userId}")
    public ResponseEntity<List<FavoritePost>> getFavoritePostsByUserId(@PathVariable Integer userId) {
        List<FavoritePost> favoritePosts = favoritePostService.getFavoritePostsByUserId(userId);
        return ResponseEntity.ok(favoritePosts);
    }

    // Eliminar una publicación favorita
    @DeleteMapping("/{userId}/{postId}")
    public ResponseEntity<Void> removeFavoritePost(@PathVariable Integer userId, @PathVariable String postId) {
        favoritePostService.removeFavoritePost(userId, postId);
        return ResponseEntity.noContent().build();
    }

    // Verificar si una publicación es favorita de un usuario
    @GetMapping("/check/{userId}/{postId}")
    public ResponseEntity<Boolean> isPostFavoritedByUser(@PathVariable Integer userId, @PathVariable String postId) {
        boolean isFavorited = favoritePostService.isPostFavoritedByUser(userId, postId);
        return ResponseEntity.ok(isFavorited);
    }
}
