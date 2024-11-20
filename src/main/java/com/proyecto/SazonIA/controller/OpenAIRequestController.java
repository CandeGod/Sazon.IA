package com.proyecto.SazonIA.controller;

import org.springframework.data.domain.Page;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import com.google.gson.Gson;

@RestController
@RequestMapping("/chatbot")
@Tag(name = "ChatBot", description = "Operations related to the chatbot service")
public class OpenAIRequestController {

    private final OpenAIRequestService openAIRequestService;
    private final Gson gson = new Gson();

    @Autowired
    public OpenAIRequestController(OpenAIRequestService openAIRequestService) {
        this.openAIRequestService = openAIRequestService;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @Operation(summary = "Generate recommendations using the OpenAI API")
    @ApiResponse(responseCode = "200", description = "Recommendations retrieved successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class)))
    @ApiResponse(responseCode = "400", description = "Invalid input parameters", content = @Content)
    @PostMapping("/recommendations")
    public ResponseEntity<String> getRecommendations(@RequestParam @Min(1) Integer user_id, @RequestParam String prompt) {

        if (!openAIRequestService.userExists(user_id)) {
            return new ResponseEntity<>(gson.toJson(Map.of("error", "User with specified ID not found")), HttpStatus.NOT_FOUND);
        }

        String recommendations = openAIRequestService.getRecommendations(user_id, prompt);
        return new ResponseEntity<>(gson.toJson(Map.of("recommendations", recommendations)), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @Operation(summary = "Retrieve a user's recommendation history with pagination")
    @ApiResponse(responseCode = "200", description = "User's recommendation history retrieved successfully", content = {
            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = OpenAIRequest.class))) })
    @GetMapping("/history/{userId}")
    public ResponseEntity<Page<OpenAIRequest>> getHistoryById(
            @PathVariable @Min(1) Integer userId,
            @RequestParam(value = "page", defaultValue = "0") @Min(0) int page,
            @RequestParam(value = "size", defaultValue = "5") @Min(2) @Max(20) int size) {

        Page<OpenAIRequest> history = openAIRequestService.getHistoryById(userId, page, size);
        return new ResponseEntity<>(history, HttpStatus.OK);
    }

    @Operation(summary = "Delete the specified user's recommendation history")
    @ApiResponse(responseCode = "200", description = "User's history deleted successfully", content = {
            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = OpenAIRequest.class))) })
    @DeleteMapping("/history/{user_id}")
    public ResponseEntity<String> deleteHistoryById(@PathVariable @Min(1) Integer user_id) {

        if (!openAIRequestService.userExists(user_id)) {
            return new ResponseEntity<>(gson.toJson(Map.of("error", "User with specified ID not found")), HttpStatus.NOT_FOUND);
        }

        openAIRequestService.delete(user_id);
        return new ResponseEntity<>(gson.toJson(Map.of("message", "User's history deleted successfully")), HttpStatus.OK);
    }
}
