package com.proyecto.SazonIA.repository;

import com.proyecto.SazonIA.model.FavoritePost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import java.util.Optional;

public interface FavoritePostRepository extends JpaRepository<FavoritePost, Integer> {
    List<FavoritePost> findByUserId(Integer userId);
    
    boolean existsByUserIdAndPostId(Integer userId, String postId);
    
    Optional<FavoritePost> findByUserIdAndPostId(Integer userId, String postId); // Para buscar el favorito
}