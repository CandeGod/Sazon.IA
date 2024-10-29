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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.SazonIA.model.CommentRecipe;
import com.proyecto.SazonIA.service.CommentRecipeService;

@RestController
@RequestMapping("/comment")
@CrossOrigin(origins = "*")
@Tag(name = "Comments to recipe", description = "Operations related to Comments in a recipe in Sazón.IA")
public class CommentRecipeController {
    @Autowired
    private CommentRecipeService commentService;

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
    @GetMapping("/{idComment}")
    public ResponseEntity<CommentRecipe> getById(@PathVariable Integer idComment) {
        return new ResponseEntity<>(commentService.getById(idComment), HttpStatus.OK);
    }

    @Operation(summary = "Save a new comment")
    @ApiResponse(responseCode = "200", description = "The comment has been saved", content = {
            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CommentRecipe.class))) })
    @ApiResponse(responseCode = "500", description = "Internal server error", content = {
            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CommentRecipe.class))) })
    @PostMapping
    public ResponseEntity<?> save(@RequestBody CommentRecipe comment) {
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
    @PutMapping("/{idComment}")
    public ResponseEntity<?> update(@PathVariable Integer idComment, @RequestBody CommentRecipe comment) {
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
    @DeleteMapping("/{idComment}")
    public ResponseEntity<?> delete(@PathVariable Integer idComment) {
        commentService.delete(idComment);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}