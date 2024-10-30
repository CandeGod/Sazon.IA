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
@RequestMapping("/comment")
@CrossOrigin(origins = "*")
@Tag(name = "Comments from recipes", description = "Operations related to Comments in a recipe in Sazón.IA")
public class CommentRecipeController {
    @Autowired
    private CommentRecipeService commentService;

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private UserService userService;

    // @Operation(summary = "Get all Comments")
    // @ApiResponse(responseCode = "200", description = "Found Comments", content =
    // {
    // @Content(mediaType = "application/json", array = @ArraySchema(schema =
    // @Schema(implementation = CommentRecipe.class))) })
    // @ApiResponse(responseCode = "500", description = "Internal server error",
    // content = {
    // @Content(mediaType = "application/json", array = @ArraySchema(schema =
    // @Schema(implementation = CommentRecipe.class))) })
    // @ApiResponse(responseCode = "404", description = "No comments registered",
    // content = {
    // @Content(mediaType = "application/json", array = @ArraySchema(schema =
    // @Schema(implementation = CommentRecipe.class))) })
    // @GetMapping
    // public List<CommentRecipe> getAll() {
    // return commentService.getAll();
    // }

    @Operation(summary = "Get a comment by Id")
    @ApiResponse(responseCode = "200", description = "The comment has been found", content = {
            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CommentRecipe.class))) })
    @ApiResponse(responseCode = "500", description = "Internal server error", content = {
            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CommentRecipe.class))) })
    @ApiResponse(responseCode = "404", description = "No comments registered", content = {
            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CommentRecipe.class))) })
    @GetMapping(value = "GetById", params = { "idComment" })
    public ResponseEntity<CommentRecipe> getById(@RequestParam(value = "idComment", required = true) Integer idComment) {
        return new ResponseEntity<>(commentService.getById(idComment), HttpStatus.OK);
    }

    @Operation(summary = "Save a new comment")
    @ApiResponse(responseCode = "200", description = "The comment has been saved", content = {
            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CommentRecipe.class))) })
    @ApiResponse(responseCode = "500", description = "Internal server error", content = {
            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CommentRecipe.class))) })
    @PostMapping(value = "SaveComment", params = { "idRecipe", "idUser" })
    public ResponseEntity<?> save(@RequestBody CommentRecipe comment, @RequestParam(value = "idRecipe", required = true) Integer idRecipe, @RequestParam(value = "idUser", required = true) Integer idUser) {
        comment.setComment_time_stamp(Timestamp.valueOf(LocalDateTime.now()) + "");
        comment.setUser(userService.getById(idUser));
        comment.setRecipe(recipeService.getById(idRecipe));
        commentService.save(comment);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @Operation(summary = "Update an existing comment by Id")
    @ApiResponse(responseCode = "200", description = "The comment has been updated", content = {
            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CommentRecipe.class))) })
    @ApiResponse(responseCode = "500", description = "Internal server error", content = {
            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CommentRecipe.class))) })
    @ApiResponse(responseCode = "404", description = "The comment was not found", content = {
            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CommentRecipe.class))) })
    @PutMapping(value = "UpdateComment", params = { "idComment" })
    public ResponseEntity<?> update(@RequestParam(value = "idComment", required = true) Integer idComment, @RequestBody CommentRecipe comment) {
        CommentRecipe aux = commentService.getById(idComment);
        comment.setUser(aux.getUser());
        comment.setRecipe(aux.getRecipe());
        comment.setComment_time_stamp(aux.getComment_time_stamp());
        commentService.save(comment);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Delete a comment by Id")
    @ApiResponse(responseCode = "200", description = "The comment has been deleted", content = {
            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CommentRecipe.class))) })
    @ApiResponse(responseCode = "500", description = "Internal server error", content = {
            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CommentRecipe.class))) })
    @ApiResponse(responseCode = "404", description = "The comment was not found", content = {
            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CommentRecipe.class))) })
    @DeleteMapping(value = "DeleteComment", params = { "idComment" })
    public ResponseEntity<?> delete(@RequestParam(value = "idComment", required = true) Integer idComment) {
        commentService.delete(idComment);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}