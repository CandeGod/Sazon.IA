package com.proyecto.SazonIA.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.SazonIA.model.Recipe;
import com.proyecto.SazonIA.model.User;
import com.proyecto.SazonIA.service.RecipeService;
import com.proyecto.SazonIA.service.UserService;

@RestController
@RequestMapping("/recipe")
@CrossOrigin(origins = "*")
@Tag(name = "Recipes", description = "Operations related to recipes in Sazón.IA")
public class RecipeController {

        @Autowired
        private RecipeService recipeService;

        @Autowired
        private UserService userService;

        @Operation(summary = "Get all Recipes")
        @ApiResponse(responseCode = "200", description = "Found Recipes", content = {
                        @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Recipe.class))) })
        @GetMapping
        public List<Recipe> getAll() {
                return recipeService.getAllRecipes();
        }

        @Operation(summary = "Get a recipe by Id")
        @ApiResponse(responseCode = "200", description = "The recipe has been found", content = {
                        @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Recipe.class))) })
        @ApiResponse(responseCode = "500", description = "Internal server error", content = {
                        @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Recipe.class))) })
        @ApiResponse(responseCode = "404", description = "The recipe was not found", content = {
                        @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Recipe.class))) })
        @GetMapping("/{idRecipe}")
        public ResponseEntity<Recipe> getById(@PathVariable Integer idRecipe) {
                return new ResponseEntity<>(recipeService.getById(idRecipe), HttpStatus.OK);
        }

        @Operation(summary = "Save a new recipe")
        @ApiResponse(responseCode = "200", description = "The recipe has saved", content = {
                        @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Recipe.class))) })
        @ApiResponse(responseCode = "500", description = "Internal server error", content = {
                        @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Recipe.class))) })
        @PostMapping("/{idUser}")
        public ResponseEntity<?> save(@RequestBody Recipe recipe, @PathVariable Integer idUser) {
                User user = userService.getById(idUser);
                recipe.setUser(user);
                recipeService.save(recipe);
                return new ResponseEntity<>(HttpStatus.OK);

        }

        @Operation(summary = "Update a recipe")
        @ApiResponse(responseCode = "200", description = "The recipe has been updated", content = {
                        @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Recipe.class))) })
        @ApiResponse(responseCode = "500", description = "Internal server error", content = {
                        @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Recipe.class))) })
        @ApiResponse(responseCode = "404", description = "The recipe was not found", content = {
                        @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Recipe.class))) })
        @PutMapping("/{idRecipe}")
        public ResponseEntity<?> update(@RequestBody Recipe recipe, @PathVariable Integer idRecipe) {
                recipeService.save(recipe);
                return new ResponseEntity<>(HttpStatus.OK);
        }

        @Operation(summary = "Delete a recipe by id")
        @ApiResponse(responseCode = "200", description = "The recipe has been found", content = {
                        @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Recipe.class))) })
        @ApiResponse(responseCode = "500", description = "Internal server error", content = {
                        @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Recipe.class))) })
        @ApiResponse(responseCode = "404", description = "The recipe was not found", content = {
                        @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Recipe.class))) })
        @DeleteMapping("/{idRecipe}")
        public ResponseEntity<?> delete(@PathVariable Integer idRecipe) {
                recipeService.delete(idRecipe);
                return new ResponseEntity<>(HttpStatus.OK);

        }
}