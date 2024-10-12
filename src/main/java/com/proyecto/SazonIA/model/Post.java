package com.proyecto.SazonIA.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Document(collection = "Posts")
public class Post {

    @Id
    @JsonIgnore // Ignorar en la documentación y serialización
    private String postId;

    @NotNull(message = "User ID must not be null")
    private Long userId;

    @NotBlank(message = "Title must not be blank")
    @Size(max = 100, message = "Title cannot exceed 100 characters")
    private String title;

    @NotBlank(message = "Content must not be blank")
    @Size(min = 10, max = 1000, message = "Content cannot exceed 1000 characters")
    private String content;

    @JsonIgnore // Ignorar en la documentación y serialización
    private String postDate;

    private List<String> mediaUrls;

    @JsonIgnore // Ignorar en la documentación y serialización
    private List<CommentPost> comments;

    @Min(value = 0, message = "Rating must be between 0 and 5")
    @Max(value = 5, message = "Rating must be between 0 and 5")
    private int rating;

    // Constructor por defecto
    public Post() {
        this.mediaUrls = new ArrayList<>();
        this.comments = new ArrayList<>();
        this.rating = 0; // Inicialmente sin valoración
        this.postDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")); // Establecer la fecha automáticamente
    }

    // Constructor con parámetros
    public Post(Long userId, String title, String content) {
        this(); // Llamada al constructor por defecto
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.postId = UUID.randomUUID().toString(); // Generación del ID
    }

    // Getters y Setters
    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId; // Permitir establecer el ID, si es necesario
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate; // Permitir establecer la fecha, si es necesario
    }

    public List<String> getMediaUrls() {
        return mediaUrls;
    }

    public void setMediaUrls(List<String> mediaUrls) {
        this.mediaUrls = mediaUrls;
    }

    public List<CommentPost> getComments() {
        return comments;
    }

    public void setComments(List<CommentPost> comments) {
        this.comments = comments;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
