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

@Document(collection = "Posts")
public class Post {

    @Id
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String postId;

    @NotNull(message = "User ID must not be null")
    private Integer userId; 

    @NotBlank(message = "Title must not be blank")
    @Size(max = 100, message = "Title cannot exceed 100 characters")
    private String title;

    @NotBlank(message = "Content must not be blank")
    @Size(min = 10, max = 1000, message = "Content cannot exceed 1000 characters")
    private String content;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String postDate;

    //private List<String> mediaUrls;


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
    public Post() {
        //this.mediaUrls = new ArrayList<>();
        this.postDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    // Constructor con parámetros
    public Post(Integer userId, String title, String content) { // Cambiado a Long para userId
        this(); 
        this.userId = userId; // Establecer el id del User (referencia a MySQL)
        this.title = title;
        this.content = content;
        this.postId = UUID.randomUUID().toString(); 
    }

    // Getters y Setters
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
        this.postDate = postDate;
    }

    /*public List<String> getMediaUrls() {
        return mediaUrls;
    }

    public void setMediaUrls(List<String> mediaUrls) {
        this.mediaUrls = mediaUrls;
    }*/

}
