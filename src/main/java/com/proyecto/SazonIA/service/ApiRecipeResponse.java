package com.proyecto.SazonIA.service;



import java.util.List;

import com.proyecto.SazonIA.model.ApiRecipe;


public class ApiRecipeResponse {
    
    private List<ApiRecipe> recipes;

    public List<ApiRecipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<ApiRecipe> recipes) {
        this.recipes = recipes;
    }
    
    
}




