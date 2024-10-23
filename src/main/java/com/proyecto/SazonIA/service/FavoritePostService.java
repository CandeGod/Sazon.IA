package com.proyecto.SazonIA.service;

import com.proyecto.SazonIA.model.FavoritePost;
import com.proyecto.SazonIA.model.Post;
import com.proyecto.SazonIA.repository.FavoritePostRepository;
import com.proyecto.SazonIA.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FavoritePostService {

    @Autowired
    private FavoritePostRepository favoritePostRepository; // Repositorio para los favoritos
    private UserRepository userRepository; 

    @Autowired
    private PostService postService; // Para verificar si el post existe

    // Agregar un post a favoritos
    public void addFavoritePost(Integer userId, String postId) {
        // Verifica si el usuario existe
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User not found"); // O lanza una excepción adecuada
        }

        // Verifica si el post existe
        Optional<Post> postOptional = postService.getPostById(postId);
        if (!postOptional.isPresent()) {
            throw new RuntimeException("Post not found"); // O lanza una excepción adecuada
        }

        // Verifica si el favorito ya existe
        if (favoritePostRepository.existsByUserIdAndPostId(userId, postId)) {
            throw new RuntimeException("Post already added to favorites");
        }

        // Guarda el favorito
        FavoritePost favoritePost = new FavoritePost();
        favoritePost.setUserId(userId);
        favoritePost.setPostId(postId);
        favoritePostRepository.save(favoritePost);
    }

    // Remover un post de favoritos
    public void removeFavoritePost(Integer userId, String postId) {
        // Verifica si el post existe
        postService.getPostById(postId).orElseThrow(() -> new RuntimeException("Post not found"));

        FavoritePost favoritePost = favoritePostRepository.findByUserIdAndPostId(userId, postId)
                .orElseThrow(() -> new RuntimeException("Favorite post not found"));
        favoritePostRepository.delete(favoritePost);
    }

    // Obtener una publicación favorita por ID
    public Post getFavoritePostContent(Integer userId, String postId) {
        // Verifica si el post existe
        postService.getPostById(postId).orElseThrow(() -> new RuntimeException("Post not found"));

        // Verifica si el post está en los favoritos
        FavoritePost favoritePost = favoritePostRepository.findByUserIdAndPostId(userId, postId)
                .orElseThrow(() -> new RuntimeException("Favorite post not found"));

        // Retorna la publicación
        return postService.getPostById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
    }
}
