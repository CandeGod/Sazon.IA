package com.proyecto.SazonIA.service;

import com.proyecto.SazonIA.exception.UserNotFoundException;
import com.proyecto.SazonIA.model.Post;
import com.proyecto.SazonIA.model.RatingPost;
import com.proyecto.SazonIA.model.User;
import com.proyecto.SazonIA.repository.PostRepository;
import com.proyecto.SazonIA.repository.RatingPostRepository;
import com.proyecto.SazonIA.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

@Service
public class RatingPostService {

    @Autowired
    private RatingPostRepository ratingPostRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;

    // Método para crear una nueva valoración
    public RatingPost createRatingPost(String postId, Integer userId, Integer value) {
        // Verificar si el usuario está registrado
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            throw new UserNotFoundException("User not found");
        }

        // Verificar si ya existe una valoración para el mismo postId y userId
        List<RatingPost> existingRatings = ratingPostRepository.findByPostId(postId);
        for (RatingPost rating : existingRatings) {
            if (rating.getUserId().equals(userId)) {
                throw new IllegalArgumentException("User has already rated this post");
            }
        }

        // Crear el objeto RatingPost
        RatingPost ratingPost = new RatingPost(postId, userId, value);

        // Guardar la valoración en la base de datos
        RatingPost savedRating = ratingPostRepository.save(ratingPost);

        // Obtener el post asociado
        Post post = postRepository.findById(postId).orElse(null);
        if (post != null) {
            // Asegurarse de que la lista de valoraciones no sea nula
            if (post.getRatings() == null) {
                post.setRatings(new ArrayList<>());
            }
            // Agregar la nueva valoración a la lista de valoraciones del post
            post.getRatings().add(savedRating);
            // Guardar el post actualizado
            postRepository.save(post);
        }

        return savedRating; // Retornar la valoración guardada
    }

    // Método para obtener todas las valoraciones de un post específico
    public List<RatingPost> getRatingsByPostId(String postId) {
        return ratingPostRepository.findByPostId(postId);
    }

    // Método para actualizar una valoración existente
    public RatingPost updateRating(String ratingId, Integer value) {
        Optional<RatingPost> optionalRating = ratingPostRepository.findById(ratingId);
        if (optionalRating.isPresent()) {
            RatingPost rating = optionalRating.get();
            String postId = rating.getPostId(); // Obtener el postId de la valoración

            // Actualiza el valor de la valoración
            rating.setValue(value);

            // Guarda la valoración actualizada
            RatingPost updatedRating = ratingPostRepository.save(rating);

            // Obtener el post asociado
            Post post = postRepository.findById(postId).orElse(null);
            if (post != null) {
                // Asegurarse de que la lista de valoraciones no sea nula
                if (post.getRatings() == null) {
                    post.setRatings(new ArrayList<>());
                }
                // Actualizar la valoración en la lista de valoraciones del post
                for (int i = 0; i < post.getRatings().size(); i++) {
                    if (post.getRatings().get(i).getRatingId().equals(ratingId)) {
                        post.getRatings().set(i, updatedRating); // Actualizar la valoración en la lista
                        break;
                    }
                }
                // Guardar el post actualizado
                postRepository.save(post);
            }

            return updatedRating; // Retornar la valoración actualizada
        } else {
            return null; // O lanza una excepción si la valoración no se encuentra
        }
    }

    // Método para eliminar una valoración por su ID
    public boolean deleteRating(String ratingId) {
        if (ratingPostRepository.existsById(ratingId)) {
            ratingPostRepository.deleteById(ratingId);
            return true; // Devuelve true si la eliminación fue exitosa
        }
        return false; // Devuelve false si la valoración no se encontró
    }

    // Método para obtener una valoración por su ID
    public Optional<RatingPost> getRatingById(String ratingId) {
        return ratingPostRepository.findById(ratingId);
    }
}
