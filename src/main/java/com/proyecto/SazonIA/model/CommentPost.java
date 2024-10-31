package com.proyecto.SazonIA.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Document(collection = "CommentOnPost")
public class CommentPost {

    @Id
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String commentId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @NotBlank(message = "Post ID must not be blank")
    private String postId;

    @JsonProperty(access = JsonProperty.Access.AUTO)
    @NotNull(message = "User ID must not be null")
    private Integer userId;

    @NotBlank(message = "Content must not be blank")
    @Size(min = 10, max = 500, message = "Content cannot exceed 500 characters")
    private String content;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String commentDate;

    private int ratingSum = 0;  // Suma total de las calificaciones
    private int ratingCount = 0; // Contador de calificaciones

    // Método para obtener el promedio
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public double getRatingAverage() {
        return ratingCount > 0 ? (double) ratingSum / ratingCount : 0.0;
    }

    // Métodos de modificación del ratingSum y ratingCount
    public void addRating(int value) {
        ratingSum += value;
        ratingCount++;
    }

    public void updateRating(int oldValue, int newValue) {
        ratingSum = ratingSum - oldValue + newValue;
    }

    public void removeRating(int value) {
        ratingSum -= value;
        ratingCount--;
    }

    // Constructor por defecto
    public CommentPost() {
        this.commentId = UUID.randomUUID().toString();
        this.commentDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    // Constructor con parámetros
    public CommentPost(String postId, Integer userId, String content) {
        this();
        this.postId = postId;
        this.userId = userId;
        this.content = content;
    }

    // Getters y Setters
    public String getCommentId() {
        return commentId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(String commentDate) {
        this.commentDate = commentDate;
    }
}
