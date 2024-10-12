package com.proyecto.SazonIA.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.SazonIA.model.Follower;
import com.proyecto.SazonIA.model.FollowerPK;
import com.proyecto.SazonIA.model.User;

@Repository
public interface FollowerRepository extends JpaRepository<Follower, FollowerPK> {
    
    // Encontrar a quién sigue un usuario
    List<Follower> findByUser(User user);

    // Encontrar los seguidores de un usuario
    List<Follower> findByFollowed(User followed);

    // Encontrar una relación de seguimiento específica
    Optional<Follower> findByUserAndFollowed(User user, User followed);

    
}
