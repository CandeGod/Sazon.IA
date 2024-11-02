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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.SazonIA.model.Recipe;
import com.proyecto.SazonIA.model.User;
import com.proyecto.SazonIA.service.RecipeService;
import com.proyecto.SazonIA.service.UserService;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/recipes")
@CrossOrigin(origins = "*")
@Tag(name = "Recipes from users", description = "Operations related to recipes in Sazón.IA")
public class RecipeController {

        @Autowired
        private RecipeService recipeService;

        @Autowired
        private UserService userService;

        @Operation(summary = "Get recipes by pagination")
        @GetMapping(value = "pagination", params = { "page", "pageSize" })
        public List<Recipe> getAllPagination(
                        @RequestParam(value = "page", defaultValue = "0", required = false) int page,
                        @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
                return recipeService.getAll(page, pageSize);
        }

        @Operation(summary = "Get a recipe by Id")
        @ApiResponse(responseCode = "200", description = "The recipe has been found", content = {
                        @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Recipe.class))) })
        @ApiResponse(responseCode = "500", description = "Internal server error", content = {
                        @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Recipe.class))) })
        @ApiResponse(responseCode = "404", description = "The recipe was not found", content = {
                        @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Recipe.class))) })
        @GetMapping(params = { "idRecipe" })
        public ResponseEntity<Recipe> getById(@RequestParam(value = "idRecipe", required = true) Integer idRecipe) {
                return new ResponseEntity<>(recipeService.getById(idRecipe), HttpStatus.OK);
        }

        @Operation(summary = "Save a new recipe")
        @ApiResponse(responseCode = "200", description = "The recipe has saved", content = {
                        @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Recipe.class))) })
        @ApiResponse(responseCode = "500", description = "Internal server error", content = {
                        @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Recipe.class))) })
        @PostMapping(params = { "idUser" })
        public ResponseEntity<?> save(@RequestBody Recipe recipe,
                        @RequestParam(value = "idUser", required = true) Integer idUser) {
                User user = userService.getById(idUser);
                recipe.setUser(user);
                if (user == null) {
                        return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
                }
                recipe.setRecipe_time_stamp(Timestamp.valueOf(LocalDateTime.now()) + "");
                recipeService.save(recipe);
                return new ResponseEntity<>("Recipe saved", HttpStatus.OK);

        }

        @Operation(summary = "Update a recipe")
        @ApiResponse(responseCode = "200", description = "The recipe has been updated", content = {
                        @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Recipe.class))) })
        @ApiResponse(responseCode = "500", description = "Internal server error", content = {
                        @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Recipe.class))) })
        @ApiResponse(responseCode = "404", description = "The recipe was not found", content = {
                        @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Recipe.class))) })
        @PutMapping(params = { "idRecipe" })
        public ResponseEntity<?> update(@RequestBody Recipe recipe,
                        @RequestParam(value = "idRecipe", required = true) Integer idRecipe) {
                Recipe aux = recipeService.getById(idRecipe);
                User usAux = userService.getById(aux.getUser().getUser_id());
                recipe.setUser(usAux);
                recipe.setRecipe_time_stamp(aux.getRecipe_time_stamp());
                recipeService.save(recipe);
                return new ResponseEntity<>("Recipe Updated", HttpStatus.OK);
        }

        @Operation(summary = "Delete a recipe by id")
        @ApiResponse(responseCode = "200", description = "The recipe has been found", content = {
                        @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Recipe.class))) })
        @ApiResponse(responseCode = "500", description = "Internal server error", content = {
                        @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Recipe.class))) })
        @ApiResponse(responseCode = "404", description = "The recipe was not found", content = {
                        @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Recipe.class))) })
        @DeleteMapping(params = { "idRecipe" })
        public ResponseEntity<?> delete(@RequestParam(value = "idRecipe", required = true) Integer idRecipe) {
                Recipe recipe = recipeService.getById(idRecipe);
                if (recipe == null) {
                        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
                recipeService.delete(idRecipe);
                return new ResponseEntity<>("Recipe Deleted", HttpStatus.OK);

        }

        @Operation(summary = "Get all recipes from a user paginated")
        @ApiResponse(responseCode = "200", description = "The recipes have been found", content = {
                        @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Recipe.class))) })
        @ApiResponse(responseCode = "500", description = "Internal server error", content = {
                        @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Recipe.class))) })
        @ApiResponse(responseCode = "404", description = "No recipes found", content = {
                        @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Recipe.class))) })
        @GetMapping( value = "FromUser",params = { "idUser" })
        public ResponseEntity<?> getRecipesByUser(@RequestParam(value = "idUser", required = true) Integer idUser,
                        @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
                        @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize) {
                User user = userService.getById(idUser);
                if (user == null) {
                        return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
                }
                page = page * pageSize;
                return new ResponseEntity<>(recipeService.getRecipesByUser(idUser, pageSize, page), HttpStatus.OK);
        }
}