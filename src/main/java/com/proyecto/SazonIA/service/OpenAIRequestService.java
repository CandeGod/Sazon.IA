package com.proyecto.SazonIA.service;


import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.time.LocalDate;

import com.proyecto.SazonIA.repository.OpenAIRequestRepository;
import com.proyecto.SazonIA.repository.UserRepository;
import com.proyecto.SazonIA.model.OpenAIRequest;
import com.proyecto.SazonIA.model.User;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class OpenAIRequestService {
    
    @Value("${openai.api.key}")
    private String openaiApiKey;

    private final OpenAIRequestRepository openAIRequestRepository;

    private final UserRepository userRepository;

    private final RestTemplate restTemplate;

    @Autowired
    public OpenAIRequestService(OpenAIRequestRepository openAIRequestRepository, UserRepository userRepository, RestTemplate restTemplate) {
        this.openAIRequestRepository = openAIRequestRepository;
        this.userRepository = userRepository;
        this.restTemplate = restTemplate;
    }
    
    public String getRecommendations(Integer userId, String prompt) {
        // Recuperar usuario por ID
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Realizar llamada a la API de OpenAI
        String url = "https://api.openai.com/v1/chat/completions";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(openaiApiKey);

        JSONObject body = new JSONObject();
        body.put("model", "gpt-3.5-turbo");
        body.put("messages", new JSONArray().put(new JSONObject().put("role", "user").put("content", prompt)));

        HttpEntity<String> request = new HttpEntity<>(body.toString(), headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);

        // Procesar la respuesta
        JSONObject responseBody = new JSONObject(response.getBody());
        String recommendations = responseBody.getJSONArray("choices").getJSONObject(0).getJSONObject("message").getString("content");

        // Guardar la solicitud en la base de datos
        OpenAIRequest openAIRequest = new OpenAIRequest();
        openAIRequest.setUser(user);
        openAIRequest.setPrompt(prompt);
        openAIRequest.setRecommendations(recommendations);
        openAIRequest.setRequestDate(LocalDate.now());

        openAIRequestRepository.save(openAIRequest);

        return recommendations;
    }

    public List<OpenAIRequest> getAll(){
        return openAIRequestRepository.findAll();
    }

    public List<OpenAIRequest> getHistoryById(Integer id){
        return openAIRequestRepository.findByUserId(id);
    }

    public void delete (Integer id){
        openAIRequestRepository.deleteByUserId(id);
    }

    
    public boolean userExists(Integer userId) {
        return userRepository.existsById(userId);
    }





    
}
