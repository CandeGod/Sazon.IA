package com.proyecto.SazonIA.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
@IdClass(FollowerPK.class)
public class Follower {
    @Id
    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "followedId", nullable = false)
    private User followed;


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getFollowed() {
        return followed;
    }

    public void setFollowed(User followed) {
        this.followed = followed;
    }

    @Override
    public String toString() {
        return "Follower [user=" + user + ", followed=" + followed + "]";
    }

   

}
