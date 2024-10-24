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

import com.proyecto.SazonIA.model.Comment;
import com.proyecto.SazonIA.model.Recipe;
import com.proyecto.SazonIA.service.RecipeService;
import com.proyecto.SazonIA.service.CommentService;

@RestController
@RequestMapping("/recipe")
@CrossOrigin(origins = "*")
@Tag(name = "Recipes", description = "Operations related to recipes in Sazón.IA")
public class RecipeController {

        @Autowired
        private RecipeService recipeService;
        @Autowired
        private CommentService commentService;

        @Operation(summary = "Get all Recipes")
        @ApiResponse(responseCode = "200", description = "Found Recipes", content = {
                        @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Recipe.class))) })
        @GetMapping
        public List<Recipe> getAll() {
                return recipeService.getAll();
        }

        @Operation(summary = "Get a recipe by Id")
        @ApiResponse(responseCode = "200", description = "The recipe has been found", content = {
                        @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Recipe.class))) })
        @ApiResponse(responseCode = "500", description = "Internal server error", content = {
                        @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Recipe.class))) })
        @ApiResponse(responseCode = "404", description = "The recipe was not found", content = {
                        @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Recipe.class))) })
        @GetMapping("/{idRecipe}")
        public ResponseEntity<Recipe> get(@PathVariable String idRecipe) {
                return new ResponseEntity<>(recipeService.getById(idRecipe), HttpStatus.OK);
        }

        @Operation(summary = "Save a new recipe")
        @ApiResponse(responseCode = "200", description = "The recipe has saved", content = {
                        @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Recipe.class))) })
        @ApiResponse(responseCode = "500", description = "Internal server error", content = {
                        @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Recipe.class))) })
        @PostMapping
        public ResponseEntity<?> add(@RequestBody Recipe recipe) {
                recipe.setRecipeId(recipeService.getIdRecipe());
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
        public ResponseEntity<?> update(@RequestBody Recipe recipe, @PathVariable String idRecipe) {
                recipe.setId(idRecipe);
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
        public ResponseEntity<?> delete(@PathVariable String idRecipe) {
                recipeService.delete(idRecipe);
                return new ResponseEntity<>(HttpStatus.OK);

        }

        @Operation(summary = "Add a new comment to a recipe")
        @ApiResponse(responseCode = "200", description = "The comment has been added", content = {
                        @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Recipe.class))) })
        @ApiResponse(responseCode = "500", description = "Internal server error", content = {
                        @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Recipe.class))) })
        @ApiResponse(responseCode = "404", description = "The recipe was not found", content = {
                        @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Recipe.class))) })
        @PutMapping("/addComment/{idRecipe}/comment")
        public ResponseEntity<?> addComment(@RequestBody Comment comment, @PathVariable String idRecipe) {
                comment.setCommentId(commentService.getIdComment());
                recipeService.addCommentToRecipe(idRecipe, comment);
                return new ResponseEntity<>(HttpStatus.OK);
        }

        @Operation(summary = "Add a reply to a comment")
        @ApiResponse(responseCode = "200", description = "The reply has been added", content = {
                        @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Recipe.class))) })
        @ApiResponse(responseCode = "500", description = "Internal server error", content = {
                        @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Recipe.class))) })
        @ApiResponse(responseCode = "404", description = "The recipe was not found", content = {
                        @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Recipe.class))) })
        @PutMapping("/addReply/{idRecipe}/comment/{commentId}/reply")
        public ResponseEntity<?> addNewReply(@RequestBody Comment reply, @PathVariable String idRecipe,
                        @PathVariable String commentId) {
                                reply.setCommentId(commentService.getIdComment());
                recipeService.addReplyToComment(idRecipe, commentId, reply);
                return new ResponseEntity<>(HttpStatus.OK);
        }

        @Operation(summary = "Delete a comment from a recipe")
        @ApiResponse(responseCode = "200", description = "The comment has been deleted", content = {
                        @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Recipe.class))) })
        @ApiResponse(responseCode = "500", description = "Internal server error", content = {
                        @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Recipe.class))) })
        @ApiResponse(responseCode = "404", description = "The recipe was not found", content = {
                        @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Recipe.class))) })
        @DeleteMapping("/deleteComment/{id}/comment/{commentId}")
        public ResponseEntity<?> deleteComment(@PathVariable String id, @PathVariable String commentId) {
                recipeService.deleteCommentFromRecipe(id, commentId);
                return new ResponseEntity<>(HttpStatus.OK);
        }

        @Operation(summary = "Delete a reply from a comment")
        @ApiResponse(responseCode = "200", description = "The reply has been deleted", content = {
                        @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Recipe.class))) })
        @ApiResponse(responseCode = "500", description = "Internal server error", content = {
                        @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Recipe.class))) })
        @ApiResponse(responseCode = "404", description = "The recipe was not found", content = {
                        @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Recipe.class))) })
        @DeleteMapping("/deleteReply/{idRecipe}/comment/{commentId}/reply/{replyId}")
        public ResponseEntity<?> deleteReply(@PathVariable String idRecipe, @PathVariable String commentId,
                        @PathVariable String replyId) {
                recipeService.deleteReplyFromComment(idRecipe, commentId, replyId);
                return new ResponseEntity<>(HttpStatus.OK);
        }

        @Operation(summary = "Update a comment from a recipe")
        @ApiResponse(responseCode = "200", description = "The comment has been updated", content = {
                        @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Recipe.class))) })
        @ApiResponse(responseCode = "500", description = "Internal server error", content = {
                        @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Recipe.class))) })
        @ApiResponse(responseCode = "404", description = "The recipe was not found", content = {
                        @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Recipe.class))) })
        @PutMapping("/updateComment/{idRecipe}/{idComment}/comment")
        public ResponseEntity<?> updateCommentFromReply(@RequestBody Comment comment, @PathVariable String idRecipe,
                        @PathVariable String idComment) {
                recipeService.updateCommentFromRecipe(idRecipe, idComment, comment);
                return new ResponseEntity<>(HttpStatus.OK);
        }

        @Operation(summary = "Update a reply from a comment")
        @ApiResponse(responseCode = "200", description = "The reply has been updated", content = {
                        @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Recipe.class))) })
        @ApiResponse(responseCode = "500", description = "Internal server error", content = {
                        @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Recipe.class))) })
        @ApiResponse(responseCode = "404", description = "The recipe was not found", content = {
                        @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Recipe.class))) })
        @PutMapping("/updateReply/{idRecipe}/comment/{commentId}/reply")
        public ResponseEntity<?> updateReplyFromComment(@RequestBody Comment reply, @PathVariable String idRecipe,
                        @PathVariable String commentId) {
                recipeService.updateReplyFromComment(idRecipe, commentId, reply);
                return new ResponseEntity<>(HttpStatus.OK);
        }

}