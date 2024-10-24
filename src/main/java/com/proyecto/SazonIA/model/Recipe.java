package com.proyecto.SazonIA.model;

import org.springframework.data.mongodb.core.mapping.MongoId;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import com.fasterxml.jackson.annotation.JsonProperty;

@Document(collection = "Recipe")
public class Recipe {
    
    @MongoId
    @Field(targetType = FieldType.OBJECT_ID)
    @NotBlank(message = "Id is mandatory")
    @JsonProperty("id")
    private String id;
    @NotBlank(message = "recipeId is mandatory")
    @Field(name = "recipeId")
    @JsonProperty("recipeId")
    private int recipeId;
    @NotBlank(message = "Name is mandatory")
    @Field(name = "name")
    @JsonProperty("name")
    private String name;
    @Field(name = "ingredients")
    @JsonProperty("ingredients")
    private List<String> ingredients;
    @NotBlank(message = "Instructions are mandatory")
    @Field(name = "instructions")
    @JsonProperty("instructions")
    private String instructions;
    @NotBlank(message = "Preparation Time is mandatory")
    @Field(name = "preparationTime")
    @JsonProperty("preparationTime")
    private String preparationTime;
    @NotBlank(message = "Difficulty is mandatory")
    @Field(name = "difficulty")
    @JsonProperty("difficulty")
    private String difficulty;
    @Field(name = "comments")
    @JsonProperty("comments")
    private List<Comment> comments;

    public Recipe() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(String preparationTime) {
        this.preparationTime = preparationTime;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "id='" + id + '\'' +
                ", recipeId=" + recipeId +
                ", name='" + name + '\'' +
                ", ingredients='" + ingredients + '\'' +
                ", instructions='" + instructions + '\'' +
                ", preparationTime='" + preparationTime + '\'' +
                ", difficulty='" + difficulty + '\'' +
                ", comments='" + comments + '\n' +
                '}';
    }
}