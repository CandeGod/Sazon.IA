package com.proyecto.SazonIA.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

@Entity
public class OpenAIRequest {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id")
    private Integer id;

    @NotNull(message = "User must not be null")
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotBlank(message = "Prompt must not be blank")
    @Size(min = 1, max = 500, message = "Prompt must not exceed 500 characters")
    @Column(nullable = false, length = 500)
    private String prompt;

    @Size(max = 1000, message = "Recommendations must not exceed 1000 characters")
    @Column(length = 1000)
    private String recommendations;

    @NotNull(message = "Request date must not be null")
    @Column(name = "requestDate", nullable = false)
    private LocalDate requestDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public String getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(String recommendations) {
        this.recommendations = recommendations;
    }

    public LocalDate getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDate requestDate) {
        this.requestDate = requestDate;
    }
}
