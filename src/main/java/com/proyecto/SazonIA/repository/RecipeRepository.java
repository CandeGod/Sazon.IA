package com.proyecto.SazonIA.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proyecto.SazonIA.model.Recipe;

public interface RecipeRepository extends JpaRepository<Recipe, Integer>{

}