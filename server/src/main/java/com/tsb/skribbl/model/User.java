package com.tsb.skribbl.model;

import java.util.Objects;
import java.util.UUID;

public class User {
    private final UUID userId;
    private final String username;
    private int score;

    public User(String username) {
        this.userId = UUID.randomUUID();
        this.username = username;
        this.score = 0;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public int getScore() {
        return score;
    }

    public void increaseScore(int score) {
        this.score += score;
    }

    public void resetScore() {
        this.score = 0;
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
