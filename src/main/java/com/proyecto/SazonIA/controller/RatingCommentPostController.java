package com.proyecto.SazonIA.controller;

import com.proyecto.SazonIA.model.RatingCommentPost;
import com.proyecto.SazonIA.service.RatingCommentPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/ratings/comments")
@Tag(name = "Rating Comments", description = "Operations related to ratings of comments in Sazón.IA")
public class RatingCommentPostController {

    @Autowired
    private RatingCommentPostService ratingCommentService;

    @Operation(summary = "Create a new rating for a comment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Rating created successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RatingCommentPost.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PostMapping
    public ResponseEntity<RatingCommentPost> createRatingComment(@Valid @RequestBody RatingCommentPost ratingComment) {
        RatingCommentPost createdRating = ratingCommentService.createRatingComment(ratingComment.getCommentId(), ratingComment.getUserId(), ratingComment.getValue());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRating);
    }

    @Operation(summary = "Get ratings by comment ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ratings retrieved successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RatingCommentPost.class))),
            @ApiResponse(responseCode = "404", description = "Comment not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping("/comment/{commentId}")
    public ResponseEntity<List<RatingCommentPost>> getRatingsByCommentId(@PathVariable String commentId) {
        List<RatingCommentPost> ratings = ratingCommentService.getRatingsByCommentId(commentId);
        return ResponseEntity.ok(ratings);
    }

    @Operation(summary = "Update an existing rating")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rating updated successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RatingCommentPost.class))),
            @ApiResponse(responseCode = "404", description = "Rating not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PutMapping("/{ratingId}")
    public ResponseEntity<RatingCommentPost> updateRatingComment(@PathVariable String ratingId, @Valid @RequestBody RatingCommentPost ratingComment) {
        RatingCommentPost updatedRating = ratingCommentService.updateRatingComment(ratingId, ratingComment.getValue());
        if (updatedRating != null) {
            return ResponseEntity.ok(updatedRating);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary = "Delete a rating by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Rating deleted successfully", content = @Content),
            @ApiResponse(responseCode = "404", description = "Rating not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @DeleteMapping("/{ratingId}")
    public ResponseEntity<Void> deleteRatingComment(@PathVariable String ratingId) {
        boolean deleted = ratingCommentService.deleteRatingComment(ratingId);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary = "Get a rating by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rating found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RatingCommentPost.class))),
            @ApiResponse(responseCode = "404", description = "Rating not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping("/{ratingId}")
    public ResponseEntity<RatingCommentPost> getRatingById(@PathVariable String ratingId) {
        Optional<RatingCommentPost> rating = ratingCommentService.getRatingById(ratingId);
        return rating.map(ResponseEntity::ok)
                     .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
