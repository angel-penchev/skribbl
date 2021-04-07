package com.tsb.skribbl.model.game;

import java.util.Objects;
import java.util.UUID;

public class User {
    private final UUID userId;
    private final String username;
    private int score;

    public User(String username) {
        this.userId = UUID.randomUUID();
        this.username = username;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userId.equals(user.userId) && Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, username);
    }
}
