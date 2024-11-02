package com.proyecto.SazonIA.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

// import java.util.List;
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

import com.proyecto.SazonIA.model.CommentRecipe;
import com.proyecto.SazonIA.service.CommentRecipeService;
import com.proyecto.SazonIA.service.RecipeService;
import com.proyecto.SazonIA.service.UserService;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/commentsRecipes")
@CrossOrigin(origins = "*")
@Tag(name = "Comments from recipes", description = "Operations related to Comments in a recipe in Sazón.IA")
public class CommentRecipeController {
        @Autowired
        private CommentRecipeService commentService;

        @Autowired
        private RecipeService recipeService;

        @Autowired
        private UserService userService;

        @Operation(summary = "Get a comment by Id")
        @ApiResponse(responseCode = "200", description = "The comment has been found", content = {
                        @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CommentRecipe.class))) })
        @ApiResponse(responseCode = "500", description = "Internal server error", content = {
                        @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CommentRecipe.class))) })
        @ApiResponse(responseCode = "404", description = "No comments registered", content = {
                        @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CommentRecipe.class))) })
        @GetMapping(params = { "idComment" })
        public ResponseEntity<CommentRecipe> getById(
                        @RequestParam(value = "idComment", required = true) Integer idComment) {
                return new ResponseEntity<>(commentService.getById(idComment), HttpStatus.OK);
        }

        @Operation(summary = "Save a new comment")
        @ApiResponse(responseCode = "200", description = "The comment has been saved", content = {
                        @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CommentRecipe.class))) })
        @ApiResponse(responseCode = "500", description = "Internal server error", content = {
                        @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CommentRecipe.class))) })
        @PostMapping(params = { "idRecipe", "idUser" })
        public ResponseEntity<?> save(@RequestBody CommentRecipe comment,
                        @RequestParam(value = "idRecipe", required = true) Integer idRecipe,
                        @RequestParam(value = "idUser", required = true) Integer idUser) {
                comment.setComment_time_stamp(Timestamp.valueOf(LocalDateTime.now()) + "");
                comment.setUser(userService.getById(idUser));
                if (userService.getById(idUser) == null) {
                        return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);

                }
                comment.setRecipe(recipeService.getById(idRecipe));
                if (recipeService.getById(idRecipe) == null) {
                        return new ResponseEntity<>("Recipe not found", HttpStatus.NOT_FOUND);
                }
                commentService.save(comment);
                return new ResponseEntity<>("Comment Saved", HttpStatus.OK);

        }

        @Operation(summary = "Update an existing comment by Id")
        @ApiResponse(responseCode = "200", description = "The comment has been updated", content = {
                        @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CommentRecipe.class))) })
        @ApiResponse(responseCode = "500", description = "Internal server error", content = {
                        @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CommentRecipe.class))) })
        @ApiResponse(responseCode = "404", description = "The comment was not found", content = {
                        @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CommentRecipe.class))) })
        @PutMapping(params = { "idComment" })
        public ResponseEntity<?> update(@RequestParam(value = "idComment", required = true) Integer idComment,
                        @RequestBody CommentRecipe comment) {
                CommentRecipe aux = commentService.getById(idComment);
                comment.setUser(aux.getUser());
                if (commentService.getById(idComment) == null) {
                        return new ResponseEntity<>("Comment not found", HttpStatus.NOT_FOUND);

                }
                comment.setRecipe(aux.getRecipe());
                comment.setComment_time_stamp(aux.getComment_time_stamp());
                commentService.save(comment);
                return new ResponseEntity<>("Comment Updated", HttpStatus.OK);
        }

        @Operation(summary = "Delete a comment by Id")
        @ApiResponse(responseCode = "200", description = "The comment has been deleted", content = {
                        @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CommentRecipe.class))) })
        @ApiResponse(responseCode = "500", description = "Internal server error", content = {
                        @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CommentRecipe.class))) })
        @ApiResponse(responseCode = "404", description = "The comment was not found", content = {
                        @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CommentRecipe.class))) })
        @DeleteMapping(params = { "idComment" })
        public ResponseEntity<?> delete(@RequestParam(value = "idComment", required = true) Integer idComment) {
                if (commentService.getById(idComment) == null) {
                        return new ResponseEntity<>("Comment not found", HttpStatus.NOT_FOUND);
                }
                commentService.delete(idComment);
                return new ResponseEntity<>("Comment Deleted", HttpStatus.OK);
        }

        @Operation(summary = "Get all coments from a recipe paginated")
        @ApiResponse(responseCode = "200", description = "Comments from a recipe has been found", content = {
                        @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CommentRecipe.class))) })
        @ApiResponse(responseCode = "500", description = "Internal server error", content = {
                        @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CommentRecipe.class))) })
        @ApiResponse(responseCode = "404", description = "No comments found", content = {
                        @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CommentRecipe.class))) })
        @GetMapping(value = "FromRecipe", params = { "idRecipe" })
        public ResponseEntity<?> getCommentsByRecipe(
                        @RequestParam(value = "idRecipe", required = true) Integer idRecipe,
                        @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                        @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
                if (userService.getById(idRecipe) == null) {
                        return new ResponseEntity<>("Recipe not found", HttpStatus.NOT_FOUND);
                }
                page = page * pageSize;
                return new ResponseEntity<>(commentService.getCommentsByRecipe(idRecipe, pageSize, page), HttpStatus.OK);
        }

}