package com.proyecto.SazonIA.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.SazonIA.model.User;
import com.proyecto.SazonIA.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void getAll() {
        userRepository.findAll();
    }

    public User getById(Integer user_id) {
        return userRepository.findById(user_id).get();
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public void delete(Integer user_id) {
        userRepository.deleteById(user_id);
    }



}
