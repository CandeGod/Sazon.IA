package com.proyecto.SazonIA.repository;

import com.proyecto.SazonIA.model.FavoritePost;
import com.proyecto.SazonIA.model.FavoritePostId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoritePostRepository extends JpaRepository<FavoritePost, FavoritePostId> {
    List<FavoritePost> findByIdUserId(Integer userId);
    
}
