package com.proyecto.SazonIA.repository;

import com.proyecto.SazonIA.model.RatingPost;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingPostRepository extends MongoRepository<RatingPost, String> {
    // Método para encontrar todas las valoraciones asociadas a un post específico
    List<RatingPost> findByPostId(String postId);
}
