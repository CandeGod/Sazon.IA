package com.proyecto.SazonIA.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.SazonIA.model.OpenAIRequest;
import com.proyecto.SazonIA.service.OpenAIRequestService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/api/openai")
@Tag(name = "ChatBot", description = "Operations related to user chatBot")
public class OpenAIRequestController {

    private final OpenAIRequestService openAIRequestService;

    @Autowired
    public OpenAIRequestController(OpenAIRequestService openAIRequestService) {
        this.openAIRequestService = openAIRequestService;
    }

    @Operation(summary = "Get recommendations from OpenAI API")
    @ApiResponse(responseCode = "200", description = "Recommendations retrieved successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class)))
    @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    @PostMapping("/recommendations")
    public ResponseEntity<String> getRecommendations(@RequestParam Integer userId, @RequestParam String prompt) {

        if (!openAIRequestService.userExists(userId)) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        String recommendations = openAIRequestService.getRecommendations(userId, prompt);
        return new ResponseEntity<>(recommendations, HttpStatus.OK);
    }

    @Operation(summary = "get all recommendations")
    @ApiResponse(responseCode = "200", description = "Found recommendations", content = {
            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = OpenAIRequest.class))) })
    @GetMapping
    public List<OpenAIRequest> getAll() {
        return openAIRequestService.getAll();
    }

    @Operation(summary = "Get a user's history")
    @ApiResponse(responseCode = "200", description = "Found recommendations of a user", content = {
            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = OpenAIRequest.class))) })
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getHistoryByUserId(@PathVariable Integer userId) {
        // Verificar si el usuario existe
        if (!openAIRequestService.userExists(userId)) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        List<OpenAIRequest> history = openAIRequestService.getHistoryById(userId);

        if (history.isEmpty()) {
            return new ResponseEntity<>("No recommendations found for this user", HttpStatus.OK);
        }

        return new ResponseEntity<>(history, HttpStatus.OK);
    }

    @Operation(summary = "Delete a user's history")
    @ApiResponse(responseCode = "200", description = "Delete a user's history", content = {
            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = OpenAIRequest.class))) })
    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteHistoryById(@PathVariable Integer userId) {
        
        if (!openAIRequestService.userExists(userId)) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        openAIRequestService.delete(userId);
        return new ResponseEntity<>("History deleted successfully", HttpStatus.OK);
    }

}
