package com.proyecto.SazonIA.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.SazonIA.service.OpenAIRequestService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/api/openai")
public class OpenAIRequestController {
    
    private final OpenAIRequestService openAIRequestService;

    @Autowired
    public OpenAIRequestController(OpenAIRequestService openAIRequestService) {
        this.openAIRequestService = openAIRequestService;
    }

    @Operation(summary = "Get recommendations from OpenAI API")
    @ApiResponse(responseCode = "200", description = "Recommendations retrieved successfully", 
                 content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class)))
    @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    @PostMapping("/recommendations")
    public ResponseEntity<String> getRecommendations(@RequestParam Integer userId, @RequestParam String prompt) {
        String recommendations = openAIRequestService.getRecommendations(userId, prompt);
        return new ResponseEntity<>(recommendations, HttpStatus.OK);
    }
}
