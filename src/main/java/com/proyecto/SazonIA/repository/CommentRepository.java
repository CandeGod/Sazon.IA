package com.proyecto.SazonIA.repository;

import com.proyecto.SazonIA.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends MongoRepository<Comment, String> {
    List<Comment> findByPostId(String postId);

    // Método para obtener comentarios paginados por postId
    Page<Comment> findByPostId(String postId, Pageable pageable);
}