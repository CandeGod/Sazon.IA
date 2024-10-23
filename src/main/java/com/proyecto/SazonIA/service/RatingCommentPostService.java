package com.proyecto.SazonIA.service;

import com.proyecto.SazonIA.exception.UserNotFoundException;
import com.proyecto.SazonIA.model.CommentPost;
import com.proyecto.SazonIA.model.RatingCommentPost;
import com.proyecto.SazonIA.model.User;
import com.proyecto.SazonIA.repository.CommentPostRepository;
import com.proyecto.SazonIA.repository.RatingCommentPostRepository;
import com.proyecto.SazonIA.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

@Service
public class RatingCommentPostService {

    @Autowired
    private RatingCommentPostRepository ratingCommentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentPostRepository commentPostRepository;

    // Método para crear una nueva valoración de un comentario
    public RatingCommentPost createRatingComment(String commentId, Integer userId, Integer value) {
        // Verificar si el usuario está registrado
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            throw new UserNotFoundException("User not found");
        }

        // Verificar si ya existe una valoración para el mismo commentId y userId
        List<RatingCommentPost> existingRatings = ratingCommentRepository.findByCommentId(commentId);
        for (RatingCommentPost rating : existingRatings) {
            if (rating.getUserId().equals(userId)) {
                throw new IllegalArgumentException("User has already rated this comment");
            }
        }

        // Crear el objeto RatingComment
        RatingCommentPost ratingComment = new RatingCommentPost(commentId, userId, value);

        // Guardar la valoración en la base de datos
        RatingCommentPost savedRating = ratingCommentRepository.save(ratingComment);

        // Obtener el comentario asociado
        CommentPost comment = commentPostRepository.findById(commentId).orElse(null);
        if (comment != null) {
            // Asegurarse de que la lista de valoraciones no sea nula
            if (comment.getRatings() == null) {
                comment.setRatings(new ArrayList<>());
            }
            // Agregar la nueva valoración a la lista de valoraciones del comentario
            comment.getRatings().add(savedRating);
            // Guardar el comentario actualizado
            commentPostRepository.save(comment);
        }

        return savedRating; // Retornar la valoración guardada
    }

    // Método para obtener todas las valoraciones de un comentario específico
    public List<RatingCommentPost> getRatingsByCommentId(String commentId) {
        return ratingCommentRepository.findByCommentId(commentId);
    }

    // Método para actualizar una valoración existente
    public RatingCommentPost updateRatingComment(String ratingId, Integer value) {
        Optional<RatingCommentPost> optionalRating = ratingCommentRepository.findById(ratingId);
        if (optionalRating.isPresent()) {
            RatingCommentPost rating = optionalRating.get();
            String commentId = rating.getCommentId(); // Obtener el commentId de la valoración

            // Actualiza el valor de la valoración
            rating.setValue(value);

            // Guarda la valoración actualizada
            RatingCommentPost updatedRating = ratingCommentRepository.save(rating);

            // Obtener el comentario asociado
            CommentPost comment = commentPostRepository.findById(commentId).orElse(null);
            if (comment != null) {
                // Asegurarse de que la lista de valoraciones no sea nula
                if (comment.getRatings() == null) {
                    comment.setRatings(new ArrayList<>());
                }
                // Actualizar la valoración en la lista de valoraciones del comentario
                for (int i = 0; i < comment.getRatings().size(); i++) {
                    if (comment.getRatings().get(i).getRatingId().equals(ratingId)) {
                        comment.getRatings().set(i, updatedRating); // Actualizar la valoración en la lista
                        break;
                    }
                }
                // Guardar el comentario actualizado
                commentPostRepository.save(comment);
            }

            return updatedRating; // Retornar la valoración actualizada
        } else {
            return null; // O lanza una excepción si la valoración no se encuentra
        }
    }

    // Método para eliminar una valoración por su ID
    public boolean deleteRatingComment(String ratingId) {
        if (ratingCommentRepository.existsById(ratingId)) {
            ratingCommentRepository.deleteById(ratingId);
            return true; // Devuelve true si la eliminación fue exitosa
        }
        return false; // Devuelve false si la valoración no se encontró
    }

    // Método para obtener una valoración por su ID
    public Optional<RatingCommentPost> getRatingById(String ratingId) {
        return ratingCommentRepository.findById(ratingId);
    }
}
