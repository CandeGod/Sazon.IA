package com.proyecto.SazonIA.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.proyecto.SazonIA.model.Comment;
public interface CommentRepository extends MongoRepository<Comment, String>{

}