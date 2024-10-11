    package com.proyecto.SazonIA.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
    public class Follower {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int FollowerId;

        @ManyToOne
        @JoinColumn(name = "userId", nullable = false)
        private User user;

        @ManyToOne
        @JoinColumn(name = "followedId", nullable = false)  // Foreign Key referencing Users(UserId)
        private User followed;  // The one being followed

        public int getFollowerId() {
            return FollowerId;
        }

        public void setFollowerId(int followerId) {
            FollowerId = followerId;
        }

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
            return "Follower [FollowerId=" + FollowerId + ", user=" + user + ", followed=" + followed + "]";
        }

        
    }
