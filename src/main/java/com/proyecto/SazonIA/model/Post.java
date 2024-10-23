package com.proyecto.SazonIA.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
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

    private List<String> mediaUrls;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<CommentPost> comments;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<RatingPost> ratings;

    // Constructor por defecto
    public Post() {
        this.mediaUrls = new ArrayList<>();
        this.comments = new ArrayList<>();
        this.ratings = new ArrayList<>();
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

    public List<RatingPost> getRatings() {
        return ratings;
    }

    public void setRatings(List<RatingPost> ratings) {
        this.ratings = ratings;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
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

}
