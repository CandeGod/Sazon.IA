package com.proyecto.SazonIA.repository;

import com.proyecto.SazonIA.model.Post;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PostRepository extends MongoRepository<Post, String> {
}
