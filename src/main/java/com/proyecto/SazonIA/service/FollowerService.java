package com.proyecto.SazonIA.service;

import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.proyecto.SazonIA.model.Follower;
import com.proyecto.SazonIA.model.User;
import com.proyecto.SazonIA.repository.FollowerRepository;
import com.proyecto.SazonIA.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class FollowerService {
    
    @Autowired
    private FollowerRepository repo;
    @Autowired
    private UserRepository userRepository;

    // Seguir a un usuario
    public Follower followUser(User follower, User followed) {
        Follower newFollower = new Follower();
        newFollower.setUser(follower);  
        newFollower.setFollowed(followed);  
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

    public List<Follower> getFollowers(int page, int pageSize){
        PageRequest pageReq =PageRequest.of(page, pageSize);
        Page<Follower> follows = repo.findAll(pageReq);
        return follows.getContent();
    }

    // Obtener lista de usuarios a los que sigue un usuario
    public List<Follower> getFollowing(User follower) {
        return repo.findByUser(follower);
    }

    public List<Follower>getFollowing(int page, int pageSize){
        PageRequest pageReq = PageRequest.of(page, pageSize);
        Page<Follower> follows = repo.findAll(pageReq);
        return follows.getContent();   
    }

    // Método para encontrar un usuario por su ID
    public User findUserById(int userId) {
        return userRepository.findById(userId).orElse(null); 
    }

    public boolean isFollowing(User follower, User followed) {
        return repo.findByUserAndFollowed(follower, followed).isPresent();
    }
    
    

    
}
