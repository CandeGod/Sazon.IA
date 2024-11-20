package com.proyecto.SazonIA.controller;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.proyecto.SazonIA.model.RatingPost;
import com.proyecto.SazonIA.service.RatingPostService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;

import java.util.Map;
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
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE,
        RequestMethod.PUT })
public class RatingPostController {

    @Autowired
    private RatingPostService ratingService;

    private final Gson gson = new Gson();

    @Operation(summary = "Create a new rating")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Rating created successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RatingPost.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PostMapping
    public ResponseEntity<String> createRating(@Valid @RequestBody RatingPost rating) {
        RatingPost createdRating = ratingService.createRatingPost(rating.getPostId(), rating.getUserId(),
                rating.getValue());
        Map<String, Object> response = Map.of("status", "success", "data", createdRating);
        String jsonResponse = gson.toJson(response);
        return ResponseEntity.status(HttpStatus.CREATED).body(jsonResponse);
    }

    @Operation(summary = "Update an existing rating")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rating updated successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RatingPost.class))),
            @ApiResponse(responseCode = "404", description = "Rating not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PutMapping("/{ratingId}")
    public ResponseEntity<String> updateRating(@PathVariable String ratingId,
            @Valid @RequestBody RatingPost rating) {
        RatingPost updatedRating = ratingService.updateRating(ratingId, rating.getValue());
        if (updatedRating != null) {
            Map<String, Object> response = Map.of("status", "success", "data", updatedRating);
            String jsonResponse = gson.toJson(response);
            return ResponseEntity.ok(jsonResponse);
        } else {
            Map<String, String> errorResponse = Map.of("status", "error", "message", "Rating not found");
            String jsonResponse = gson.toJson(errorResponse);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(jsonResponse);
        }
    }

    @Operation(summary = "Delete a rating by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Rating deleted successfully", content = @Content),
            @ApiResponse(responseCode = "404", description = "Rating not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @DeleteMapping("/{ratingId}")
    public ResponseEntity<String> deleteRating(@PathVariable String ratingId) {
        boolean deleted = ratingService.deleteRating(ratingId);
        if (deleted) {
            Map<String, String> response = Map.of("status", "success", "message", "Rating deleted successfully");
            String jsonResponse = gson.toJson(response);
            return ResponseEntity.ok(jsonResponse);
        } else {
            Map<String, String> errorResponse = Map.of("status", "error", "message", "Rating not found");
            String jsonResponse = gson.toJson(errorResponse);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(jsonResponse);
        }
    }

    @Operation(summary = "Get a rating by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rating found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RatingPost.class))),
            @ApiResponse(responseCode = "404", description = "Rating not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping("/{ratingId}")
    public ResponseEntity<String> getRatingById(@PathVariable String ratingId) {
        Optional<RatingPost> rating = ratingService.getRatingById(ratingId);
        if (rating.isPresent()) {
            Map<String, Object> response = Map.of("status", "success", "data", rating.get());
            String jsonResponse = gson.toJson(response);
            return ResponseEntity.ok(jsonResponse);
        } else {
            Map<String, String> errorResponse = Map.of("status", "error", "message", "Rating not found");
            String jsonResponse = gson.toJson(errorResponse);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(jsonResponse);
        }
    }
}
