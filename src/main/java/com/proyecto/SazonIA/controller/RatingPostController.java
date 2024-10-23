package com.proyecto.SazonIA.controller;

import com.proyecto.SazonIA.model.RatingPost;
import com.proyecto.SazonIA.service.RatingPostService;
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
@RequestMapping("/ratings")
@Tag(name = "Ratings Post", description = "Operations related to ratings of posts in Sazón.IA")
public class RatingPostController {

    @Autowired
    private RatingPostService ratingService;

    @Operation(summary = "Create a new rating")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Rating created successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RatingPost.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PostMapping
    public ResponseEntity<RatingPost> createRating(@Valid @RequestBody RatingPost rating) {
        RatingPost createdRating = ratingService.createRatingPost(rating.getPostId(), rating.getUserId(), rating.getValue());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRating);
    }

    @Operation(summary = "Get ratings by post ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ratings retrieved successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RatingPost.class))),
            @ApiResponse(responseCode = "404", description = "Post not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping("/post/{postId}")
    public ResponseEntity<List<RatingPost>> getRatingsByPostId(@PathVariable String postId) {
        List<RatingPost> ratings = ratingService.getRatingsByPostId(postId);
        return ResponseEntity.ok(ratings);
    }

    @Operation(summary = "Update an existing rating")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rating updated successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RatingPost.class))),
            @ApiResponse(responseCode = "404", description = "Rating not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PutMapping("/{ratingId}")
    public ResponseEntity<RatingPost> updateRating(@PathVariable String ratingId, @Valid @RequestBody RatingPost rating) {
        RatingPost updatedRating = ratingService.updateRating(ratingId, rating.getValue());
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
    public ResponseEntity<Void> deleteRating(@PathVariable String ratingId) {
        boolean deleted = ratingService.deleteRating(ratingId);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Operation(summary = "Get a rating by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rating found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RatingPost.class))),
            @ApiResponse(responseCode = "404", description = "Rating not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping("/{ratingId}")
    public ResponseEntity<RatingPost> getRatingById(@PathVariable String ratingId) {
        Optional<RatingPost> rating = ratingService.getRatingById(ratingId);
        return rating.map(ResponseEntity::ok)
                     .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
