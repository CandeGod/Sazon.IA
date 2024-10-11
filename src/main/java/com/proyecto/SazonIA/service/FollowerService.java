package com.proyecto.SazonIA.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.SazonIA.model.Follower;
import com.proyecto.SazonIA.model.User;
import com.proyecto.SazonIA.repository.FollowerRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class FollowerService {
    
    @Autowired
    private FollowerRepository repo;

    // Seguir a un usuario
    public Follower followUser(User follower, User followed) {
        Follower newFollower = new Follower();
        newFollower.setUser(follower);  // El que sigue
        newFollower.setFollowed(followed);  // El que es seguido
        return repo.save(newFollower);
    }

    // Dejar de seguir a un usuario
    public void unfollowUser(User follower, User followed) {
        Optional<Follower> existingFollower = repo.findByUserAndFollowed(follower, followed);
        if (existingFollower.isPresent()) {
            repo.delete(existingFollower.get());
        }
    }

    // Obtener lista de seguidores de un usuario
    public List<Follower> getFollowers(User followed) {
        return repo.findByFollowed(followed);
    }

    // Obtener lista de usuarios a los que sigue un usuario
    public List<Follower> getFollowing(User follower) {
        return repo.findByUser(follower);
    }

    
}
