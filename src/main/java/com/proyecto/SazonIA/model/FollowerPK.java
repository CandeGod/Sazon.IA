package com.proyecto.SazonIA.model;

import java.io.Serializable;
import java.util.Objects;

public class FollowerPK implements Serializable{
    private User user;
    private User followed;

    
    public FollowerPK() {}

    
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof FollowerPK followerPK))
            return false;
        return user.getUserId() == followerPK.user.getUserId() && Objects.equals(followed, followerPK.followed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, followed);
    }

   
    public User getUser() {
        return user;
    }

    public User getFollowed() {
        return followed;
    }
}
