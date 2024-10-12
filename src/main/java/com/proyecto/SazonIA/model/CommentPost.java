package com.proyecto.SazonIA.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Document(collection = "Comments")
public class CommentPost {
    
    @Id
    private String commentId;

    @NotBlank(message = "Post ID must not be blank")
    private String postId;

    @NotNull(message = "User ID must not be null")
    private Long userId;

    @NotBlank(message = "Content must not be blank")
    @Size(min = 10, max = 500, message = "Content cannot exceed 500 characters")
    private String content;

    private String commentDate;

    // Constructor por defecto
    public CommentPost() {
        this.commentId = UUID.randomUUID().toString(); // Generación automática del ID
        this.commentDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")); // Establecer la fecha automáticamente
    }

    // Constructor con parámetros
    public CommentPost(String postId, Long userId, String content) {
        this(); // Llamada al constructor por defecto
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
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
