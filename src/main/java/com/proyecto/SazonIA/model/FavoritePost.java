package com.proyecto.SazonIA.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "FavoritePost")
public class FavoritePost {

    @EmbeddedId
    private FavoritePostId id;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public FavoritePost() {
        this.createdAt = LocalDateTime.now();
    }

    public FavoritePost(FavoritePostId id) {
        this.id = id;
        this.createdAt = LocalDateTime.now();
    }

    public FavoritePostId getId() {
        return id;
    }

    public void setId(FavoritePostId id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
