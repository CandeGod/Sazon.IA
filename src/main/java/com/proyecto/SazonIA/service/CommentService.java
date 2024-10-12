package com.proyecto.SazonIA.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.SazonIA.model.Comment;
import com.proyecto.SazonIA.repository.CommentRepository;
@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    
    public List<Comment> getAll() {
        return commentRepository.findAll();
    }

    public void save(Comment comment) {
        commentRepository.save(comment);
    }

    public Comment getById(String id) {
        return commentRepository.findById(id).get();
    }

    public void delete(String id) {
        commentRepository.deleteById(id);
    }
}