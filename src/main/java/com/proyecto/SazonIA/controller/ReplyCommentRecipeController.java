package com.proyecto.SazonIA.controller;

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
import com.proyecto.SazonIA.model.ReplyCommentRecipe;
import com.proyecto.SazonIA.service.ReplyCommentRecipeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/replycomment")
@CrossOrigin(origins = "*")
@Tag(name = "ReplyComment", description = "Operations related to Replies in a recipe in Sazón.IA")
public class ReplyCommentRecipeController {
    @Autowired
    private ReplyCommentRecipeService replyCommentService;

    // @Operation(summary = "Get all Comments")
    // @ApiResponse(responseCode = "200", description = "Found Comments", content =
    // {
    // @Content(mediaType = "application/json", array = @ArraySchema(schema =
    // @Schema(implementation = CommentRecipe.class))) })
    // @GetMapping
    // public List<ReplyCommentRecipe> getAll() {
    // return replyCommentService.getAll();
    // }

    @Operation(summary = "Get a reply by Id")
    @ApiResponse(responseCode = "200", description = "The reply has been found", content = {
            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CommentRecipe.class))) })
    @ApiResponse(responseCode = "500", description = "Internal server error", content = {
            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CommentRecipe.class))) })
    @ApiResponse(responseCode = "404", description = "No comments registered", content = {
            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CommentRecipe.class))) })
    @GetMapping("/{idReply}")
    public ReplyCommentRecipe getById(@PathVariable Integer idReply) {
        return replyCommentService.getById(idReply);
    }

    @Operation(summary = "Save a new reply")
    @ApiResponse(responseCode = "200", description = "The reply has been saved", content = {
            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CommentRecipe.class))) })
    @ApiResponse(responseCode = "500", description = "Internal server error", content = {
            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CommentRecipe.class))) })
    @PostMapping
    public ResponseEntity<?> save(@RequestBody ReplyCommentRecipe reply) {
        replyCommentService.save(reply);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Update an existing reply by Id")
    @ApiResponse(responseCode = "200", description = "The reply has been updated", content = {
            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CommentRecipe.class))) })
    @ApiResponse(responseCode = "500", description = "Internal server error", content = {
            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CommentRecipe.class))) })
    @ApiResponse(responseCode = "404", description = "The reply was not found", content = {
            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CommentRecipe.class))) })
    @PutMapping("/{idReply}")
    public ResponseEntity<?> update(@PathVariable Integer idReply, @RequestBody ReplyCommentRecipe reply) {
        replyCommentService.save(reply);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Delete a reply by Id")
    @ApiResponse(responseCode = "200", description = "The reply has been deleted", content = {
            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CommentRecipe.class))) })
    @ApiResponse(responseCode = "500", description = "Internal server error", content = {
            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CommentRecipe.class))) })
    @ApiResponse(responseCode = "404", description = "The reply was not found", content = {
            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CommentRecipe.class))) })
    @DeleteMapping("/{idReply}")
    public ResponseEntity<?> delete(@PathVariable Integer idReply) {
        replyCommentService.delete(idReply);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
