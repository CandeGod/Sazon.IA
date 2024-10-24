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
import com.proyecto.SazonIA.service.CommentService;

@RestController
@RequestMapping("/comment")
@CrossOrigin(origins = "*")
@Tag(name = "Comments", description = "Operations related to Comments in Sazón.IA")
public class CommentController {
    @Autowired
    private CommentService commentService;


    @Operation(summary = "Get all Comments")
    @ApiResponse(responseCode = "200", description = "Found Comments", content = {
            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Comment.class))) })
    @GetMapping
    public List<Comment> getAll() {
        return commentService.getAll();
    }

    @Operation(summary = "Get a comment by Id")
    @ApiResponse(responseCode = "200", description = "Found comment", content = {
            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Comment.class))) })
    @GetMapping("/{id}")
    public Comment getById(@PathVariable String id) {
        return commentService.getById(id);
    }

    @Operation(summary = "Save a new comment")
    @ApiResponse(responseCode = "200", description = "Comment saved", content = {
            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Comment.class))) })
    @PostMapping
    public void save(@RequestBody Comment comment) {
        comment.setCommentId(commentService.getIdComment());
        commentService.save(comment);
    }

    @Operation(summary = "Update an existing comment by Id")
    @ApiResponse(responseCode = "200", description = "Comment updated", content = {
            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Comment.class))) })
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody Comment comment) {
        comment.setId(id);
        commentService.save(comment);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Delete a comment by Id")
    @ApiResponse(responseCode = "200", description = "Comment deleted", content = {
            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Comment.class))) })
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        commentService.delete(id);
    }

}