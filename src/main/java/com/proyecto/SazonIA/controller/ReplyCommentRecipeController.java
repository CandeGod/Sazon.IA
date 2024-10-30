package com.proyecto.SazonIA.controller;

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
import com.proyecto.SazonIA.model.ReplyCommentRecipe;
import com.proyecto.SazonIA.service.CommentRecipeService;
import com.proyecto.SazonIA.service.ReplyCommentRecipeService;
import com.proyecto.SazonIA.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/replycomment")
@CrossOrigin(origins = "*")
@Tag(name = "Replys from comments", description = "Operations related to Replies in a recipe in Sazón.IA")
public class ReplyCommentRecipeController {
        @Autowired
        private ReplyCommentRecipeService replyCommentService;

        @Autowired
        private CommentRecipeService commentService;

        @Autowired
        private UserService userService;

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
        @GetMapping(value = "GetById", params = { "idReply" })
        public ReplyCommentRecipe getById(@RequestParam(value = "idReply", required = true) Integer idReply) {
                return replyCommentService.getById(idReply);
        }

        @Operation(summary = "Save a new reply")
        @ApiResponse(responseCode = "200", description = "The reply has been saved", content = {
                        @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CommentRecipe.class))) })
        @ApiResponse(responseCode = "500", description = "Internal server error", content = {
                        @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CommentRecipe.class))) })
        @PostMapping(value = "SaveReply", params = { "idComment", "idUser" })
        public ResponseEntity<?> save(@RequestBody ReplyCommentRecipe reply, @RequestParam(value = "idComment", required = true) Integer idComment,
                        @RequestParam(value = "idUser", required = true) Integer idUser) {
                reply.setComment(commentService.getById(idComment));
                reply.setUser(userService.getById(idUser));
                reply.setReply_time_stamp(Timestamp.valueOf(LocalDateTime.now()) + "");
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
        @PutMapping(value = "UpdateReply", params = { "idReply" })
        public ResponseEntity<?> update(@RequestParam(value = "idReply", required = true) Integer idReply, @RequestBody ReplyCommentRecipe reply) {
                ReplyCommentRecipe aux = replyCommentService.getById(idReply);
                reply.setReply_time_stamp(aux.getReply_time_stamp());
                reply.setUser(userService.getById(aux.getUser().getUser_id()));
                reply.setComment(commentService.getById(aux.getComment().getComment_id()));
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
        @DeleteMapping(value = "DeleteReply", params = { "idReply" })
        public ResponseEntity<?> delete(@RequestParam(value = "idReply", required = true) Integer idReply) {
                replyCommentService.delete(idReply);
                return new ResponseEntity<>(HttpStatus.OK);
        }

}
