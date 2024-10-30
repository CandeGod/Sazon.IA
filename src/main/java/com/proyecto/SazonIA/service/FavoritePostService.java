package com.proyecto.SazonIA.service;

import com.proyecto.SazonIA.model.FavoritePost;
import com.proyecto.SazonIA.model.FavoritePostId;
import com.proyecto.SazonIA.model.Post;
import com.proyecto.SazonIA.repository.FavoritePostRepository;
import com.proyecto.SazonIA.repository.PostRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FavoritePostService {

    @Autowired
    private FavoritePostRepository favoritePostRepository;

    @Autowired
    private PostRepository postRepository; // El repositorio de MongoDB para las publicaciones

    // Método para obtener las publicaciones guardadas como favoritas por un usuario
    public List<Post> getContentFavoritePostsByUserId(Integer userId) {
        // Buscar todas las relaciones de favoritos para el usuario
        List<FavoritePost> favoritePosts = favoritePostRepository.findByIdUserId(userId);

        // Obtener los IDs de las publicaciones favoritas
        List<String> postIds = favoritePosts.stream()
                .map(favoritePost -> favoritePost.getId().getPostId())
                .collect(Collectors.toList());

        // Recuperar el contenido de las publicaciones favoritas desde MongoDB
        return postRepository.findAllById(postIds); // Método de MongoRepository para obtener varias publicaciones por
                                                    // sus IDs
    }

    // Guardar una publicación favorita
    public FavoritePost saveFavoritePost(Integer userId, String postId) {
        FavoritePostId favoritePostId = new FavoritePostId(userId, postId);
        FavoritePost favoritePost = new FavoritePost(favoritePostId);
        return favoritePostRepository.save(favoritePost);
    }

    // Obtener todas las publicaciones favoritas de un usuario
    public List<FavoritePost> getFavoritePostsByUserId(Integer userId) {
        return favoritePostRepository.findByIdUserId(userId);
    }

    // Eliminar una publicación favorita por ID de usuario y publicación
    public void removeFavoritePost(Integer userId, String postId) {
        FavoritePostId favoritePostId = new FavoritePostId(userId, postId);
        favoritePostRepository.deleteById(favoritePostId);
    }

    // Verificar si una publicación está marcada como favorita
    public boolean isPostFavoritedByUser(Integer userId, String postId) {
        FavoritePostId favoritePostId = new FavoritePostId(userId, postId);
        Optional<FavoritePost> favoritePost = favoritePostRepository.findById(favoritePostId);
        return favoritePost.isPresent();
    }
}
