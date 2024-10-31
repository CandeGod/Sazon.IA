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
@RequestMapping("/chatbot")
@Tag(name = "ChatBot", description = "Operations related to the chatbot service")
public class OpenAIRequestController {

    private final OpenAIRequestService openAIRequestService;

    @Autowired
    public OpenAIRequestController(OpenAIRequestService openAIRequestService) {
        this.openAIRequestService = openAIRequestService;
    }

    @Operation(summary = "Generate recommendations using the OpenAI API")
    @ApiResponse(responseCode = "200", description = "Recommendations retrieved successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class)))
    @ApiResponse(responseCode = "400", description = "Invalid input parameters", content = @Content)
    @PostMapping("/recommendations")
    public ResponseEntity<String> getRecommendations(@RequestParam Integer user_id, @RequestParam String prompt) {

        if (!openAIRequestService.userExists(user_id)) {
            return new ResponseEntity<>("User with specified ID not found", HttpStatus.NOT_FOUND);
        }

        String recommendations = openAIRequestService.getRecommendations(user_id, prompt);
        return new ResponseEntity<>(recommendations, HttpStatus.OK);
    }

    @Operation(summary = "Retrieve all recommendations")
    @ApiResponse(responseCode = "200", description = "All recommendations retrieved successfully", content = {
            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = OpenAIRequest.class))) })
    @GetMapping
    public List<OpenAIRequest> getAll() {
        return openAIRequestService.getAll();
    }

    @Operation(summary = "Retrieve a user's recommendation history")
    @ApiResponse(responseCode = "200", description = "User's recommendation history retrieved successfully", content = {
            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = OpenAIRequest.class))) })
    @GetMapping("/history/{user_id}")
    public ResponseEntity<?> getHistoryByUserId(@PathVariable Integer user_id) {
        // Verify if the user exists
        if (!openAIRequestService.userExists(user_id)) {
            return new ResponseEntity<>("User with specified ID not found", HttpStatus.NOT_FOUND);
        }

        List<OpenAIRequest> history = openAIRequestService.getHistoryById(user_id);

        if (history.isEmpty()) {
            return new ResponseEntity<>("No recommendation history found for the specified user", HttpStatus.OK);
        }

        return new ResponseEntity<>(history, HttpStatus.OK);
    }

    @Operation(summary = "Delete the specified user's recommendation history")
    @ApiResponse(responseCode = "200", description = "User's history deleted successfully", content = {
            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = OpenAIRequest.class))) })
    @DeleteMapping("/history/{user_id}")
    public ResponseEntity<String> deleteHistoryById(@PathVariable Integer user_id) {
        
        if (!openAIRequestService.userExists(user_id)) {
            return new ResponseEntity<>("User with specified ID not found", HttpStatus.NOT_FOUND);
        }

        openAIRequestService.delete(user_id);
        return new ResponseEntity<>("User's history deleted successfully", HttpStatus.OK);
    }
}
