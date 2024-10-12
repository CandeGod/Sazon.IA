package com.proyecto.SazonIA.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.proyecto.SazonIA.model.Recipe;

public interface RecipeRepository extends MongoRepository<Recipe, String>{

}